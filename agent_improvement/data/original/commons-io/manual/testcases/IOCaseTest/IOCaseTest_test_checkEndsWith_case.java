package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import org.apache.commons.io.IOUtils.ScratchBytes;
import org.apache.commons.io.IOUtils.ScratchChars;
import org.junit.jupiter.api.Test;

public class IOCaseTest_test_checkEndsWith_case {

    private static final boolean WINDOWS = File.separatorChar == '\\';

    @Test
    void test_checkEndsWith_case() {
        assertTrue(IOCase.SENSITIVE.checkEndsWith("ABC", "BC"));
        assertFalse(IOCase.SENSITIVE.checkEndsWith("ABC", "Bc"));
        assertTrue(IOCase.INSENSITIVE.checkEndsWith("ABC", "BC"));
        assertTrue(IOCase.INSENSITIVE.checkEndsWith("ABC", "Bc"));
        assertTrue(IOCase.SYSTEM.checkEndsWith("ABC", "BC"));
        assertEquals(WINDOWS, IOCase.SYSTEM.checkEndsWith("ABC", "Bc"));
    }
}
