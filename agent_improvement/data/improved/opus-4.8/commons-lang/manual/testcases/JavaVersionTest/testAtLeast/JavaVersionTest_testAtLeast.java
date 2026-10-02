package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link JavaVersion#atLeast(JavaVersion)}, which reports whether one
 * Java version is equal to or newer than another.
 */
public class JavaVersionTest_testAtLeast extends AbstractLangTest {

    @Test
    void testAtLeast() {
        // A lower version is never "at least" a higher one.
        assertFalse(JavaVersion.JAVA_1_2.atLeast(JavaVersion.JAVA_1_5),
                "1.2 should not be at least 1.5");
        assertFalse(JavaVersion.JAVA_1_6.atLeast(JavaVersion.JAVA_1_7),
                "1.6 should not be at least 1.7");

        // A higher version is always "at least" a lower one.
        assertTrue(JavaVersion.JAVA_1_5.atLeast(JavaVersion.JAVA_1_2),
                "1.5 should be at least 1.2");

        // JAVA_0_9 (Android) is internally ranked as 1.5, so it is at least 1.5
        // but not at least 1.6.
        assertTrue(JavaVersion.JAVA_0_9.atLeast(JavaVersion.JAVA_1_5),
                "0.9 should be at least 1.5");
        assertFalse(JavaVersion.JAVA_0_9.atLeast(JavaVersion.JAVA_1_6),
                "0.9 should not be at least 1.6");
    }
}
