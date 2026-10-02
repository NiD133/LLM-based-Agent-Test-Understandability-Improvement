package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.File;
import org.junit.jupiter.api.Test;

public class IOCaseTest_test_isCaseSensitive_static {

    // Windows uses backslash as separator; its file system is case-insensitive
    private static final boolean WINDOWS = File.separatorChar == '\\';

    @Test
    void test_isCaseSensitive_static() {
        // IOCase.SENSITIVE is always case-sensitive regardless of OS
        assertTrue(IOCase.isCaseSensitive(IOCase.SENSITIVE),
                "SENSITIVE should always report case-sensitive");

        // IOCase.INSENSITIVE is never case-sensitive regardless of OS
        assertFalse(IOCase.isCaseSensitive(IOCase.INSENSITIVE),
                "INSENSITIVE should always report case-insensitive");

        // IOCase.SYSTEM reflects the current OS: Unix is case-sensitive, Windows is not
        boolean expectedSensitiveOnCurrentOS = !WINDOWS;
        assertEquals(expectedSensitiveOnCurrentOS, IOCase.isCaseSensitive(IOCase.SYSTEM),
                "SYSTEM should be case-sensitive on Unix and case-insensitive on Windows");
    }
}
