package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link JavaVersion#get(String)} maps a raw version string
 * (as reported by the {@code java.specification.version} system property)
 * onto the matching {@link JavaVersion} constant.
 */
public class JavaVersionTest_testGetJavaVersion extends AbstractLangTest {

    /**
     * Asserts that parsing {@code versionString} yields {@code expected}.
     *
     * @param expected      the enum constant the string should resolve to.
     * @param versionString the raw version string handed to {@link JavaVersion#get(String)}.
     */
    private static void assertResolvesTo(final JavaVersion expected, final String versionString) {
        assertEquals(expected, JavaVersion.get(versionString), versionString + " failed");
    }

    @Test
    void testGetJavaVersion() throws Exception {
        // Legacy "1.x" naming scheme (plus the Android-reported "0.9").
        assertResolvesTo(JavaVersion.JAVA_0_9, "0.9");
        assertResolvesTo(JavaVersion.JAVA_1_1, "1.1");
        assertResolvesTo(JavaVersion.JAVA_1_2, "1.2");
        assertResolvesTo(JavaVersion.JAVA_1_3, "1.3");
        assertResolvesTo(JavaVersion.JAVA_1_4, "1.4");
        assertResolvesTo(JavaVersion.JAVA_1_5, "1.5");
        assertResolvesTo(JavaVersion.JAVA_1_6, "1.6");
        assertResolvesTo(JavaVersion.JAVA_1_7, "1.7");
        assertResolvesTo(JavaVersion.JAVA_1_8, "1.8");

        // Modern single-number naming scheme (Java 9 onwards).
        assertEquals(JavaVersion.JAVA_9, JavaVersion.get("9"));
        assertEquals(JavaVersion.JAVA_10, JavaVersion.get("10"));
        assertEquals(JavaVersion.JAVA_11, JavaVersion.get("11"));
        assertEquals(JavaVersion.JAVA_12, JavaVersion.get("12"));
        assertEquals(JavaVersion.JAVA_13, JavaVersion.get("13"));
        assertEquals(JavaVersion.JAVA_14, JavaVersion.get("14"));
        assertEquals(JavaVersion.JAVA_15, JavaVersion.get("15"));
        assertEquals(JavaVersion.JAVA_16, JavaVersion.get("16"));
        assertEquals(JavaVersion.JAVA_17, JavaVersion.get("17"));
        assertEquals(JavaVersion.JAVA_18, JavaVersion.get("18"));
        assertEquals(JavaVersion.JAVA_19, JavaVersion.get("19"));
        assertEquals(JavaVersion.JAVA_20, JavaVersion.get("20"));
        assertEquals(JavaVersion.JAVA_21, JavaVersion.get("21"));
        assertEquals(JavaVersion.JAVA_22, JavaVersion.get("22"));
        assertEquals(JavaVersion.JAVA_23, JavaVersion.get("23"));
        assertEquals(JavaVersion.JAVA_24, JavaVersion.get("24"));
        assertEquals(JavaVersion.JAVA_25, JavaVersion.get("25"));
        assertEquals(JavaVersion.JAVA_26, JavaVersion.get("26"));
        assertEquals(JavaVersion.JAVA_27, JavaVersion.get("27"));

        // Unknown-but-plausible versions fall back to JAVA_RECENT rather than null.
        assertResolvesTo(JavaVersion.JAVA_RECENT, "1.10");
        // LANG-1384: an unrecognized high version number is also treated as "recent".
        assertEquals(JavaVersion.JAVA_RECENT, JavaVersion.get("99"), "Unhandled");

        // getJavaVersion(String) is a thin wrapper that must delegate to get(String).
        assertEquals(JavaVersion.get("1.5"), JavaVersion.getJavaVersion("1.5"), "Wrapper method failed");
    }
}
