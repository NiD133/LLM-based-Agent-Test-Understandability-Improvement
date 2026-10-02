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

public class IOCaseTest_test_checkStartsWith_functionality {

    private static final boolean WINDOWS = File.separatorChar == '\\';

    @Test
    void test_checkStartsWith_functionality() {
        assertTrue(IOCase.SENSITIVE.checkStartsWith("ABC", ""));
        assertTrue(IOCase.SENSITIVE.checkStartsWith("ABC", "A"));
        assertTrue(IOCase.SENSITIVE.checkStartsWith("ABC", "AB"));
        assertTrue(IOCase.SENSITIVE.checkStartsWith("ABC", "ABC"));
        assertFalse(IOCase.SENSITIVE.checkStartsWith("ABC", "BC"));
        assertFalse(IOCase.SENSITIVE.checkStartsWith("ABC", "C"));
        assertFalse(IOCase.SENSITIVE.checkStartsWith("ABC", "ABCD"));
        assertFalse(IOCase.SENSITIVE.checkStartsWith("", "ABC"));
        assertTrue(IOCase.SENSITIVE.checkStartsWith("", ""));
        assertFalse(IOCase.SENSITIVE.checkStartsWith("ABC", null));
        assertFalse(IOCase.SENSITIVE.checkStartsWith(null, "ABC"));
        assertFalse(IOCase.SENSITIVE.checkStartsWith(null, null));
    }
}
