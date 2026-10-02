package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class JavaVersionTest_testGetJavaVersion extends AbstractLangTest {

    @Nested
    class LegacyVersions {

        @Test
        void androidVersion() {
            assertEquals(JavaVersion.JAVA_0_9, JavaVersion.get("0.9"), "0.9 failed");
        }

        @Test
        void java1xVersions() {
            assertEquals(JavaVersion.JAVA_1_1, JavaVersion.get("1.1"), "1.1 failed");
            assertEquals(JavaVersion.JAVA_1_2, JavaVersion.get("1.2"), "1.2 failed");
            assertEquals(JavaVersion.JAVA_1_3, JavaVersion.get("1.3"), "1.3 failed");
            assertEquals(JavaVersion.JAVA_1_4, JavaVersion.get("1.4"), "1.4 failed");
            assertEquals(JavaVersion.JAVA_1_5, JavaVersion.get("1.5"), "1.5 failed");
            assertEquals(JavaVersion.JAVA_1_6, JavaVersion.get("1.6"), "1.6 failed");
            assertEquals(JavaVersion.JAVA_1_7, JavaVersion.get("1.7"), "1.7 failed");
            assertEquals(JavaVersion.JAVA_1_8, JavaVersion.get("1.8"), "1.8 failed");
        }
    }

    @Nested
    class ModernVersions {

        @Test
        void java9to27() {
            assertEquals(JavaVersion.JAVA_9,  JavaVersion.get("9"),  "9 failed");
            assertEquals(JavaVersion.JAVA_10, JavaVersion.get("10"), "10 failed");
            assertEquals(JavaVersion.JAVA_11, JavaVersion.get("11"), "11 failed");
            assertEquals(JavaVersion.JAVA_12, JavaVersion.get("12"), "12 failed");
            assertEquals(JavaVersion.JAVA_13, JavaVersion.get("13"), "13 failed");
            assertEquals(JavaVersion.JAVA_14, JavaVersion.get("14"), "14 failed");
            assertEquals(JavaVersion.JAVA_15, JavaVersion.get("15"), "15 failed");
            assertEquals(JavaVersion.JAVA_16, JavaVersion.get("16"), "16 failed");
            assertEquals(JavaVersion.JAVA_17, JavaVersion.get("17"), "17 failed");
            assertEquals(JavaVersion.JAVA_18, JavaVersion.get("18"), "18 failed");
            assertEquals(JavaVersion.JAVA_19, JavaVersion.get("19"), "19 failed");
            assertEquals(JavaVersion.JAVA_20, JavaVersion.get("20"), "20 failed");
            assertEquals(JavaVersion.JAVA_21, JavaVersion.get("21"), "21 failed");
            assertEquals(JavaVersion.JAVA_22, JavaVersion.get("22"), "22 failed");
            assertEquals(JavaVersion.JAVA_23, JavaVersion.get("23"), "23 failed");
            assertEquals(JavaVersion.JAVA_24, JavaVersion.get("24"), "24 failed");
            assertEquals(JavaVersion.JAVA_25, JavaVersion.get("25"), "25 failed");
            assertEquals(JavaVersion.JAVA_26, JavaVersion.get("26"), "26 failed");
            assertEquals(JavaVersion.JAVA_27, JavaVersion.get("27"), "27 failed");
        }
    }

    @Nested
    class EdgeCases {

        // 1.10 has no explicit mapping and its minor version > 9, so it falls back to JAVA_RECENT
        @Test
        void unknownLegacyFormatWithMinorVersionAboveNineMapsToRecent() {
            assertEquals(JavaVersion.JAVA_RECENT, JavaVersion.get("1.10"), "1.10 failed");
        }

        // Versions above the highest known constant are mapped to JAVA_RECENT (LANG-1384)
        @Test
        void unknownFutureVersionMapsToRecent() {
            assertEquals(JavaVersion.JAVA_RECENT, JavaVersion.get("99"), "Unhandled");
        }

        // getJavaVersion is a public wrapper around the package-private get method
        @Test
        void getJavaVersionDelegatesToGet() {
            assertEquals(JavaVersion.get("1.5"), JavaVersion.getJavaVersion("1.5"), "Wrapper method failed");
        }
    }
}
