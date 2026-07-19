package net.blockhost.anarchymod;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DomainsTest {

    @Test
    void matchesDefaultDomainsAndSubdomains() {
        assertTrue(Domains.contains("6b6t.org"));
        assertTrue(Domains.contains("play.6b6t.org"));
        assertTrue(Domains.contains("PLAY.6B6T.ORG:25565"));
        assertTrue(Domains.contains("8b8t.xyz"));
        assertFalse(Domains.contains("not6b6t.org"));
        assertFalse(Domains.contains("6b6t.org.example.com"));
    }

    @Test
    void matchesOnePatternWithoutConsultingOtherKnownDomains() {
        assertTrue(Domains.matches("play.6b6t.org:25565", "*.6b6t.org"));
        assertFalse(Domains.matches("8b8t.org", "*.6b6t.org"));
        assertFalse(Domains.matches("not6b6t.org", "*.6b6t.org"));
    }

    @Test
    void normalizesWhitespaceTrailingDotsAndInternationalDomains() {
        assertEquals("play.6b6t.org", Domains.normalizeHost("  PLAY.6B6T.ORG.  "));
        assertEquals("xn--e1afmkfd.xn--p1ai", Domains.normalizeHost("пример.рф"));
    }

    @Test
    void handlesPortsWithoutCorruptingIpv6Literals() {
        assertEquals("6b6t.org", Domains.normalizeHost("6b6t.org:25565"));
        assertEquals("2001:db8::1", Domains.normalizeHost("[2001:DB8::1]:25565"));
        assertEquals("2001:db8::1", Domains.normalizeHost("2001:DB8::1"));
        assertNull(Domains.normalizeHost("6b6t.org:invalid"));
        assertNull(Domains.normalizeHost("6b6t.org:70000"));
    }

    @Test
    void rejectsNullEmptyAndMalformedHosts() {
        assertFalse(Domains.contains(null));
        assertFalse(Domains.contains(""));
        assertFalse(Domains.contains("6b6t.org/path"));
        assertNull(Domains.normalizeHost("[2001:db8::1"));
        assertNull(Domains.normalizeHost("\u00ad"));
    }
}
