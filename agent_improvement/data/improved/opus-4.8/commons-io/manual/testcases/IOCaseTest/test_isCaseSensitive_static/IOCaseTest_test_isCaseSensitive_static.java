package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.jupiter.api.Test;

/**
 * Tests the static, null-safe {@link IOCase#isCaseSensitive(IOCase)} helper.
 */
public class IOCaseTest_test_isCaseSensitive_static {

    /**
     * True when running on Windows, detected via the platform file separator.
     * On Windows {@link IOCase#SYSTEM} is case-insensitive; on Unix it is case-sensitive.
     */
    private static final boolean RUNNING_ON_WINDOWS = File.separatorChar == '\\';

    @Test
    void test_isCaseSensitive_static() {
        // SENSITIVE is always case-sensitive, regardless of operating system.
        assertTrue(IOCase.isCaseSensitive(IOCase.SENSITIVE));

        // INSENSITIVE is never case-sensitive, regardless of operating system.
        assertFalse(IOCase.isCaseSensitive(IOCase.INSENSITIVE));

        // SYSTEM follows the host OS: case-sensitive everywhere except Windows.
        final boolean expectedSystemSensitivity = !RUNNING_ON_WINDOWS;
        assertEquals(expectedSystemSensitivity, IOCase.isCaseSensitive(IOCase.SYSTEM));
    }
}
