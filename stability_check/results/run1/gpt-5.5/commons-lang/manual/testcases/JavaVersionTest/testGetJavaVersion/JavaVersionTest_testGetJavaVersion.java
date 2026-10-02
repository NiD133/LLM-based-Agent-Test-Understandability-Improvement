package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class JavaVersionTest_testGetJavaVersion extends AbstractLangTest {

    @Test
    void testGetJavaVersion() throws Exception {
        assertLegacyVersion(JavaVersion.JAVA_0_9, "0.9");
        assertLegacyVersion(JavaVersion.JAVA_1_1, "1.1");
        assertLegacyVersion(JavaVersion.JAVA_1_2, "1.2");
        assertLegacyVersion(JavaVersion.JAVA_1_3, "1.3");
        assertLegacyVersion(JavaVersion.JAVA_1_4, "1.4");
        assertLegacyVersion(JavaVersion.JAVA_1_5, "1.5");
        assertLegacyVersion(JavaVersion.JAVA_1_6, "1.6");
        assertLegacyVersion(JavaVersion.JAVA_1_7, "1.7");
        assertLegacyVersion(JavaVersion.JAVA_1_8, "1.8");

        assertModernVersion(JavaVersion.JAVA_9, "9");
        assertModernVersion(JavaVersion.JAVA_10, "10");
        assertModernVersion(JavaVersion.JAVA_11, "11");
        assertModernVersion(JavaVersion.JAVA_12, "12");
        assertModernVersion(JavaVersion.JAVA_13, "13");
        assertModernVersion(JavaVersion.JAVA_14, "14");
        assertModernVersion(JavaVersion.JAVA_15, "15");
        assertModernVersion(JavaVersion.JAVA_16, "16");
        assertModernVersion(JavaVersion.JAVA_17, "17");
        assertModernVersion(JavaVersion.JAVA_18, "18");
        assertModernVersion(JavaVersion.JAVA_19, "19");
        assertModernVersion(JavaVersion.JAVA_20, "20");
        assertModernVersion(JavaVersion.JAVA_21, "21");
        assertModernVersion(JavaVersion.JAVA_22, "22");
        assertModernVersion(JavaVersion.JAVA_23, "23");
        assertModernVersion(JavaVersion.JAVA_24, "24");
        assertModernVersion(JavaVersion.JAVA_25, "25");
        assertModernVersion(JavaVersion.JAVA_26, "26");
        assertModernVersion(JavaVersion.JAVA_27, "27");

        assertEquals(JavaVersion.JAVA_RECENT, JavaVersion.get("1.10"), "1.10 failed");
        assertEquals(JavaVersion.get("1.5"), JavaVersion.getJavaVersion("1.5"), "Wrapper method failed");
        assertEquals(JavaVersion.JAVA_RECENT, JavaVersion.get("99"), "Unhandled");
    }

    private static void assertLegacyVersion(final JavaVersion expectedVersion, final String version) {
        assertEquals(expectedVersion, JavaVersion.get(version), version + " failed");
    }

    private static void assertModernVersion(final JavaVersion expectedVersion, final String version) {
        assertEquals(expectedVersion, JavaVersion.get(version));
    }
}
