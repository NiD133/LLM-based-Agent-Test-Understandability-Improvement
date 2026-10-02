package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class JavaVersionTest_testToString extends AbstractLangTest {

    /**
     * JavaVersion.toString() must return the human-readable version string
     * stored at construction time, not the enum constant name.
     *
     * The enum uses three distinct string formats:
     *   - "0.9"  – Android pseudo-version
     *   - "1.x"  – classic Java versions up through 1.8
     *   - "N"    – modern single-integer versions (9, 10, 11, ...)
     */
    @Test
    @DisplayName("toString returns the standard version string for each naming scheme")
    void testToString() {
        // Android pseudo-version (not an official Java version number)
        assertEquals("0.9", JavaVersion.JAVA_0_9.toString());

        // Classic "1.x" scheme
        assertEquals("1.2", JavaVersion.JAVA_1_2.toString());
        assertEquals("1.8", JavaVersion.JAVA_1_8.toString());

        // Modern single-integer scheme
        assertEquals("9",  JavaVersion.JAVA_9.toString());
        assertEquals("11", JavaVersion.JAVA_11.toString());
        assertEquals("21", JavaVersion.JAVA_21.toString());
    }
}
