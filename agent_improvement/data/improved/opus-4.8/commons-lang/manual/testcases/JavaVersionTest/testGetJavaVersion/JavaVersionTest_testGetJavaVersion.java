package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link JavaVersion#get(String)} and its wrapper {@link JavaVersion#getJavaVersion(String)}.
 *
 * <p>Verifies that a version string is mapped to the matching {@link JavaVersion} constant.</p>
 */
public class JavaVersionTest_testGetJavaVersion extends AbstractLangTest {

    /**
     * Asserts that {@link JavaVersion#get(String)} maps the given version string to the expected constant.
     *
     * @param expected      the constant the version string should resolve to.
     * @param versionString the version string passed to {@code get}.
     */
    private static void assertGetReturns(final JavaVersion expected, final String versionString) {
        assertEquals(expected, JavaVersion.get(versionString), versionString + " failed");
    }

    @Test
    void testGetJavaVersion() throws Exception {
        // Legacy "1.x" style version strings.
        assertGetReturns(JavaVersion.JAVA_0_9, "0.9");
        assertGetReturns(JavaVersion.JAVA_1_1, "1.1");
        assertGetReturns(JavaVersion.JAVA_1_2, "1.2");
        assertGetReturns(JavaVersion.JAVA_1_3, "1.3");
        assertGetReturns(JavaVersion.JAVA_1_4, "1.4");
        assertGetReturns(JavaVersion.JAVA_1_5, "1.5");
        assertGetReturns(JavaVersion.JAVA_1_6, "1.6");
        assertGetReturns(JavaVersion.JAVA_1_7, "1.7");
        assertGetReturns(JavaVersion.JAVA_1_8, "1.8");

        // Modern single-number version strings (Java 9 and later).
        assertGetReturns(JavaVersion.JAVA_9, "9");
        assertGetReturns(JavaVersion.JAVA_10, "10");
        assertGetReturns(JavaVersion.JAVA_11, "11");
        assertGetReturns(JavaVersion.JAVA_12, "12");
        assertGetReturns(JavaVersion.JAVA_13, "13");
        assertGetReturns(JavaVersion.JAVA_14, "14");
        assertGetReturns(JavaVersion.JAVA_15, "15");
        assertGetReturns(JavaVersion.JAVA_16, "16");
        assertGetReturns(JavaVersion.JAVA_17, "17");
        assertGetReturns(JavaVersion.JAVA_18, "18");
        assertGetReturns(JavaVersion.JAVA_19, "19");
        assertGetReturns(JavaVersion.JAVA_20, "20");
        assertGetReturns(JavaVersion.JAVA_21, "21");
        assertGetReturns(JavaVersion.JAVA_22, "22");
        assertGetReturns(JavaVersion.JAVA_23, "23");
        assertGetReturns(JavaVersion.JAVA_24, "24");
        assertGetReturns(JavaVersion.JAVA_25, "25");
        assertGetReturns(JavaVersion.JAVA_26, "26");
        assertGetReturns(JavaVersion.JAVA_27, "27");

        // Unknown / out-of-range versions fall back to the most recent known version.
        assertGetReturns(JavaVersion.JAVA_RECENT, "1.10"); // decimal greater than .9
        assertEquals(JavaVersion.JAVA_RECENT, JavaVersion.get("99"), "Unhandled"); // LANG-1384

        // The getJavaVersion wrapper must delegate to get.
        assertEquals(JavaVersion.get("1.5"), JavaVersion.getJavaVersion("1.5"), "Wrapper method failed");
    }
}
