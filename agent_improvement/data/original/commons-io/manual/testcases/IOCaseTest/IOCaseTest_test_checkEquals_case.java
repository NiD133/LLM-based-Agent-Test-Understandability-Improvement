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

public class IOCaseTest_test_checkEquals_case {

    private static final boolean WINDOWS = File.separatorChar == '\\';

    @Test
    void test_checkEquals_case() {
        assertTrue(IOCase.SENSITIVE.checkEquals("ABC", "ABC"));
        assertFalse(IOCase.SENSITIVE.checkEquals("ABC", "Abc"));
        assertTrue(IOCase.INSENSITIVE.checkEquals("ABC", "ABC"));
        assertTrue(IOCase.INSENSITIVE.checkEquals("ABC", "Abc"));
        assertTrue(IOCase.SYSTEM.checkEquals("ABC", "ABC"));
        assertEquals(WINDOWS, IOCase.SYSTEM.checkEquals("ABC", "Abc"));
    }
}
