package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class JavaVersionTest_testAtLeast extends AbstractLangTest {

    @Test
    void testAtLeast() {
        // Older Java versions do not satisfy newer minimum requirements.
        assertFalse(JavaVersion.JAVA_1_2.atLeast(JavaVersion.JAVA_1_5), "1.2 at least 1.5 passed");
        assertFalse(JavaVersion.JAVA_1_6.atLeast(JavaVersion.JAVA_1_7), "1.6 at least 1.7 passed");

        // Newer Java versions satisfy older minimum requirements.
        assertTrue(JavaVersion.JAVA_1_5.atLeast(JavaVersion.JAVA_1_2), "1.5 at least 1.2 failed");

        // Android's 0.9 version is represented internally with the same value as Java 1.5.
        assertTrue(JavaVersion.JAVA_0_9.atLeast(JavaVersion.JAVA_1_5), "0.9 at least 1.5 failed");
        assertFalse(JavaVersion.JAVA_0_9.atLeast(JavaVersion.JAVA_1_6), "0.9 at least 1.6 passed");
    }
}
