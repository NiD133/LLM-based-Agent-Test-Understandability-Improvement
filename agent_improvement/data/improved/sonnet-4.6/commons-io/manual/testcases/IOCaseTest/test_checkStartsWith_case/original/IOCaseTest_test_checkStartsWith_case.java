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

public class IOCaseTest_test_checkStartsWith_case {

    private static final boolean WINDOWS = File.separatorChar == '\\';

    @Test
    void test_checkStartsWith_case() {
        assertTrue(IOCase.SENSITIVE.checkStartsWith("ABC", "AB"));
        assertFalse(IOCase.SENSITIVE.checkStartsWith("ABC", "Ab"));
        assertTrue(IOCase.INSENSITIVE.checkStartsWith("ABC", "AB"));
        assertTrue(IOCase.INSENSITIVE.checkStartsWith("ABC", "Ab"));
        assertTrue(IOCase.SYSTEM.checkStartsWith("ABC", "AB"));
        assertEquals(WINDOWS, IOCase.SYSTEM.checkStartsWith("ABC", "Ab"));
    }
}
