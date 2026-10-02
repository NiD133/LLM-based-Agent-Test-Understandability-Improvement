package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link JavaVersion#get(String)} maps a Java specification-version
 * string to the matching {@link JavaVersion} constant, and that unrecognized or
 * out-of-range strings fall back to {@link JavaVersion#JAVA_RECENT}.
 */
public class JavaVersionTest_testGetJavaVersion extends AbstractLangTest {

    /**
     * Asserts that {@code JavaVersion.get(versionStr)} returns {@code expected}.
     *
     * @param expected   the constant the lookup should resolve to.
     * @param versionStr the version string passed to {@link JavaVersion#get(String)}.
     */
    private static void assertVersionMapsTo(final JavaVersion expected, final String versionStr) {
        assertEquals(expected, JavaVersion.get(versionStr), versionStr + " failed");
    }

    @Test
    void testGetJavaVersion() throws Exception {
        // Legacy "1.x"-style version strings map to their dedicated constants.
        assertVersionMapsTo(JavaVersion.JAVA_0_9, "0.9");
        assertVersionMapsTo(JavaVersion.JAVA_1_1, "1.1");
        assertVersionMapsTo(JavaVersion.JAVA_1_2, "1.2");
        assertVersionMapsTo(JavaVersion.JAVA_1_3, "1.3");
        assertVersionMapsTo(JavaVersion.JAVA_1_4, "1.4");
        assertVersionMapsTo(JavaVersion.JAVA_1_5, "1.5");
        assertVersionMapsTo(JavaVersion.JAVA_1_6, "1.6");
        assertVersionMapsTo(JavaVersion.JAVA_1_7, "1.7");
        assertVersionMapsTo(JavaVersion.JAVA_1_8, "1.8");

        // Modern single-number version strings map to their dedicated constants.
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

        // Strings beyond the known constants resolve to the "most recent" fallback.
        assertVersionMapsTo(JavaVersion.JAVA_RECENT, "1.10");
        // assertNull("2.10 unexpectedly worked", JavaVersion.get("2.10"));

        // getJavaVersion is a thin wrapper and must agree with get.
        assertEquals(JavaVersion.get("1.5"), JavaVersion.getJavaVersion("1.5"), "Wrapper method failed");

        // LANG-1384: an unhandled high version number still falls back to JAVA_RECENT.
        assertEquals(JavaVersion.JAVA_RECENT, JavaVersion.get("99"), "Unhandled");
    }
}
