package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.jupiter.api.Test;

public class IOCaseTest_test_isCaseSensitive_static {

    private static final boolean SYSTEM_IS_CASE_SENSITIVE = File.separatorChar != '\\';

    @Test
    void test_isCaseSensitive_static() {
        assertTrue(IOCase.isCaseSensitive(IOCase.SENSITIVE));
        assertFalse(IOCase.isCaseSensitive(IOCase.INSENSITIVE));
        assertEquals(SYSTEM_IS_CASE_SENSITIVE, IOCase.isCaseSensitive(IOCase.SYSTEM));
    }
}
