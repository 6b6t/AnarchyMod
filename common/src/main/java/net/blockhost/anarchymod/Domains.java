package net.blockhost.anarchymod;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.logging.Logger;

public class Domains {

    private static final Logger LOGGER = Logger.getLogger("AnarchyMod-Domains");
    private static final String DOMAINS_URL = "https://www.6b6t.org/api/anarchy-mod.json";

    private static final Set<String> DEFAULT = Set.of(
        "*.6b6t.org", "*.10b10t.org", "*.6b6t.cc", "*.6b6t.me",
        "*.7b7t.me", "*.8b8t.org", "*.alacity.net",
        "*.anarchypvp.pw", "*.l2x9.org", "*.simpleanarchy.org"
    );

    private static final Set<String> domains = new HashSet<>();
    private static final Gson gson = new Gson();

    static {
        DEFAULT.forEach(d -> domains.add(d.toLowerCase(Locale.ROOT)));
        loadRemote();
    }

    private static void loadRemote() {
        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(DOMAINS_URL))
                .timeout(Duration.ofSeconds(15))
                .GET()
                .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            JsonObject json = gson.fromJson(response.body(), JsonObject.class);
            JsonArray array = json.getAsJsonArray("domains");
            if (array == null) return;

            array.forEach(element ->
                domains.add(element.getAsString().toLowerCase(Locale.ROOT))
            );
        } catch (Exception e) {
            LOGGER.warning("Failed to load domains from remote, using defaults: " + e.getMessage());
        }
    }

    public static boolean contains(String input) {
        if (input == null) return false;

        String domain = input.toLowerCase(Locale.ROOT);

        if (domain.startsWith("*.")) {
            domain = domain.substring(2);
        }

        int semiColon = domain.indexOf(':');
        if (semiColon != -1) {
            domain = domain.substring(0, semiColon);
        }

        for (String entry : domains) {
            if (entry.startsWith("*.")) {
                String base = entry.substring(2);
                if (domain.equals(base) || domain.endsWith("." + base)) {
                    return true;
                }
            } else {
                if (domain.equals(entry)) {
                    return true;
                }
            }
        }

        return false;
    }
}
