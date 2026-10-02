package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class JavaVersionTest_testGetJavaVersion extends AbstractLangTest {

    @Test
    void testGetJavaVersion() throws Exception {
        assertEquals(JavaVersion.JAVA_0_9, JavaVersion.get("0.9"), "0.9 failed");

        assertEquals(JavaVersion.JAVA_1_1, JavaVersion.get("1.1"), "1.1 failed");
        assertEquals(JavaVersion.JAVA_1_2, JavaVersion.get("1.2"), "1.2 failed");
        assertEquals(JavaVersion.JAVA_1_3, JavaVersion.get("1.3"), "1.3 failed");
        assertEquals(JavaVersion.JAVA_1_4, JavaVersion.get("1.4"), "1.4 failed");
        assertEquals(JavaVersion.JAVA_1_5, JavaVersion.get("1.5"), "1.5 failed");
        assertEquals(JavaVersion.JAVA_1_6, JavaVersion.get("1.6"), "1.6 failed");
        assertEquals(JavaVersion.JAVA_1_7, JavaVersion.get("1.7"), "1.7 failed");
        assertEquals(JavaVersion.JAVA_1_8, JavaVersion.get("1.8"), "1.8 failed");

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

        assertEquals(JavaVersion.JAVA_RECENT, JavaVersion.get("1.10"), "1.10 failed");
        // assertNull("2.10 unexpectedly worked", JavaVersion.get("2.10"));

        assertEquals(JavaVersion.get("1.5"), JavaVersion.getJavaVersion("1.5"), "Wrapper method failed");

        // LANG-1384
        assertEquals(JavaVersion.JAVA_RECENT, JavaVersion.get("99"), "Unhandled");
    }
}
