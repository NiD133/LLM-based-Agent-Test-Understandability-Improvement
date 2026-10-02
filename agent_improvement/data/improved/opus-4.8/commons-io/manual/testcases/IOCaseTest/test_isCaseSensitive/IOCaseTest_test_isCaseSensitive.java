package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link IOCase#isCaseSensitive()} for each enum constant.
 */
public class IOCaseTest_test_isCaseSensitive {

    /** True when the current platform uses the Windows file separator ('\'). */
    private static final boolean RUNNING_ON_WINDOWS = File.separatorChar == '\\';

    @Test
    void test_isCaseSensitive() {
        // SENSITIVE is always case-sensitive, regardless of operating system.
        assertTrue(IOCase.SENSITIVE.isCaseSensitive());

        // INSENSITIVE is never case-sensitive, regardless of operating system.
        assertFalse(IOCase.INSENSITIVE.isCaseSensitive());

        // SYSTEM follows the platform: case-sensitive everywhere except Windows.
        final boolean expectedSystemSensitivity = !RUNNING_ON_WINDOWS;
        assertEquals(expectedSystemSensitivity, IOCase.SYSTEM.isCaseSensitive());
    }
}
