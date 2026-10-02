package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.jupiter.api.Test;

public class IOCaseTest_test_isCaseSensitive {

    private static final boolean IS_WINDOWS = File.separatorChar == '\\';

    @Test
    void test_isCaseSensitive() {
        assertTrue(IOCase.SENSITIVE.isCaseSensitive());
        assertFalse(IOCase.INSENSITIVE.isCaseSensitive());
        assertEquals(!IS_WINDOWS, IOCase.SYSTEM.isCaseSensitive());
    }
}
