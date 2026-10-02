package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class JavaVersionTest_testAtLeast extends AbstractLangTest {

    @Test
    void testAtLeast_olderVersionIsNotAtLeastNewerVersion() {
        assertFalse(JavaVersion.JAVA_1_2.atLeast(JavaVersion.JAVA_1_5), "1.2 at least 1.5 passed");
        assertFalse(JavaVersion.JAVA_1_6.atLeast(JavaVersion.JAVA_1_7), "1.6 at least 1.7 passed");
    }

    @Test
    void testAtLeast_newerVersionIsAtLeastOlderVersion() {
        assertTrue(JavaVersion.JAVA_1_5.atLeast(JavaVersion.JAVA_1_2), "1.5 at least 1.2 failed");
    }

    @Test
    void testAtLeast_androidVersion_internalValueEqualsJava15() {
        // JAVA_0_9 represents Android's Java version; its internal float value is 1.5,
        // equal to JAVA_1_5, so it passes atLeast(JAVA_1_5) but not atLeast(JAVA_1_6).
        assertTrue(JavaVersion.JAVA_0_9.atLeast(JavaVersion.JAVA_1_5), "0.9 at least 1.5 failed");
        assertFalse(JavaVersion.JAVA_0_9.atLeast(JavaVersion.JAVA_1_6), "0.9 at least 1.6 passed");
    }
}
