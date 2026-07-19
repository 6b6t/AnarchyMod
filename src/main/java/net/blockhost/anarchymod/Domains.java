package net.blockhost.anarchymod;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.net.IDN;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

public final class Domains {

    private static final Logger LOGGER = Logger.getLogger("AnarchyMod-Domains");
    private static final String DOMAINS_URL = "https://www.6b6t.org/api/anarchy-mod.json";
    private static final int MAX_RESPONSE_LENGTH = 1_048_576;
    private static final int MAX_REMOTE_DOMAINS = 4_096;

    private static final Set<String> DEFAULT = Set.of(
        "*.6b6t.org", "*.10b10t.org", "*.6b6t.cc", "*.6b6t.me",
        "*.7b7t.me", "*.8b8t.org", "*.8b8t.xyz", "*.alacity.net",
        "*.anarchypvp.pw", "*.l2x9.org", "*.simpleanarchy.org"
    );

    private static final Gson GSON = new Gson();
    private static final AtomicBoolean REMOTE_LOAD_STARTED = new AtomicBoolean();
    private static volatile Set<String> domains = DEFAULT;

    private Domains() {
    }

    static void initialize() {
        if (REMOTE_LOAD_STARTED.compareAndSet(false, true)) {
            loadRemoteAsync();
        }
    }

    private static void loadRemoteAsync() {
        try {
            HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .build();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(DOMAINS_URL))
                .timeout(Duration.ofSeconds(10))
                .header("Accept", "application/json")
                .GET()
                .build();

            client.sendAsync(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8))
                .thenAccept(Domains::applyRemoteResponse)
                .exceptionally(error -> {
                    LOGGER.warning("Failed to load domains from remote, using defaults: " + error.getMessage());
                    return null;
                });
        } catch (RuntimeException error) {
            // Domain checks must remain available even if the HTTP client cannot be initialized.
            LOGGER.warning("Failed to load domains from remote, using defaults: " + error.getMessage());
        }
    }

    private static void applyRemoteResponse(HttpResponse<String> response) {
        if (response.statusCode() != 200) {
            LOGGER.warning("Failed to load domains from remote, using defaults: HTTP " + response.statusCode());
            return;
        }

        String body = response.body();
        if (body == null || body.isBlank() || body.length() > MAX_RESPONSE_LENGTH) {
            LOGGER.warning("Failed to load domains from remote, using defaults: response is empty or too large");
            return;
        }

        JsonObject json = GSON.fromJson(body, JsonObject.class);
        JsonArray array = json == null ? null : json.getAsJsonArray("domains");
        if (array == null) {
            LOGGER.warning("Failed to load domains from remote, using defaults: missing domains array");
            return;
        }

        Set<String> updated = new HashSet<>(DEFAULT);
        int count = 0;
        for (JsonElement element : array) {
            if (count++ >= MAX_REMOTE_DOMAINS) {
                LOGGER.warning("Remote domain list exceeded the entry limit; remaining entries were ignored");
                break;
            }
            if (!element.isJsonPrimitive() || !element.getAsJsonPrimitive().isString()) {
                continue;
            }

            String entry = normalizeEntry(element.getAsString());
            if (entry != null) {
                updated.add(entry);
            }
        }

        // Readers always see either the old or the complete new immutable snapshot.
        domains = Set.copyOf(updated);
    }

    public static boolean contains(String input) {
        String domain = normalizeHost(input);
        if (domain == null) {
            return false;
        }

        for (String entry : domains) {
            if (matchesNormalized(domain, entry)) {
                return true;
            }
        }

        return false;
    }

    public static boolean matches(String input, String pattern) {
        String domain = normalizeHost(input);
        String entry = normalizeEntry(pattern);
        return domain != null && entry != null && matchesNormalized(domain, entry);
    }

    private static boolean matchesNormalized(String domain, String entry) {
        if (!entry.startsWith("*.")) {
            return domain.equals(entry);
        }

        String base = entry.substring(2);
        return domain.equals(base) || domain.endsWith("." + base);
    }

    private static String normalizeEntry(String input) {
        if (input == null) {
            return null;
        }

        String trimmed = input.trim();
        boolean wildcard = trimmed.startsWith("*.");
        String host = normalizeHost(wildcard ? trimmed.substring(2) : trimmed);
        return host == null ? null : (wildcard ? "*." : "") + host;
    }

    static String normalizeHost(String input) {
        if (input == null) {
            return null;
        }

        String host = input.trim();
        if (host.startsWith("*.")) {
            host = host.substring(2);
        }
        if (host.isEmpty()) {
            return null;
        }

        if (host.startsWith("[")) {
            int closingBracket = host.indexOf(']');
            if (closingBracket < 0 || !isValidPortSuffix(host.substring(closingBracket + 1))) {
                return null;
            }
            host = host.substring(1, closingBracket);
        } else {
            int firstColon = host.indexOf(':');
            int lastColon = host.lastIndexOf(':');
            if (firstColon >= 0 && firstColon == lastColon) {
                if (!isValidPortSuffix(host.substring(firstColon))) {
                    return null;
                }
                host = host.substring(0, firstColon);
            }
        }

        while (host.endsWith(".")) {
            host = host.substring(0, host.length() - 1);
        }
        if (host.isEmpty()) {
            return null;
        }

        try {
            // IPv6 literals contain colons and are not valid IDNs, but preserving them is safe.
            if (host.indexOf(':') >= 0) {
                return host.toLowerCase(Locale.ROOT);
            }
            String ascii = IDN.toASCII(host, IDN.USE_STD3_ASCII_RULES).toLowerCase(Locale.ROOT);
            return ascii.isEmpty() || ascii.length() > 253 ? null : ascii;
        } catch (IllegalArgumentException ignored) {
            return null;
        }
    }

    private static boolean isValidPortSuffix(String suffix) {
        if (suffix.isEmpty()) {
            return true;
        }
        if (suffix.charAt(0) != ':' || suffix.length() == 1) {
            return false;
        }

        try {
            int port = Integer.parseInt(suffix.substring(1));
            return port >= 1 && port <= 65_535;
        } catch (NumberFormatException ignored) {
            return false;
        }
    }
}
