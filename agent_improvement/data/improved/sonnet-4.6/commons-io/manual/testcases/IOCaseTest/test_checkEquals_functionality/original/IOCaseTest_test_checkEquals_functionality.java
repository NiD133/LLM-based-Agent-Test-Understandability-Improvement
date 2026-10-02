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

public class IOCaseTest_test_checkEquals_functionality {

    private static final boolean WINDOWS = File.separatorChar == '\\';

    @Test
    void test_checkEquals_functionality() {
        assertFalse(IOCase.SENSITIVE.checkEquals("ABC", ""));
        assertFalse(IOCase.SENSITIVE.checkEquals("ABC", "A"));
        assertFalse(IOCase.SENSITIVE.checkEquals("ABC", "AB"));
        assertTrue(IOCase.SENSITIVE.checkEquals("ABC", "ABC"));
        assertFalse(IOCase.SENSITIVE.checkEquals("ABC", "BC"));
        assertFalse(IOCase.SENSITIVE.checkEquals("ABC", "C"));
        assertFalse(IOCase.SENSITIVE.checkEquals("ABC", "ABCD"));
        assertFalse(IOCase.SENSITIVE.checkEquals("", "ABC"));
        assertTrue(IOCase.SENSITIVE.checkEquals("", ""));
        assertFalse(IOCase.SENSITIVE.checkEquals("ABC", null));
        assertFalse(IOCase.SENSITIVE.checkEquals(null, "ABC"));
        assertTrue(IOCase.SENSITIVE.checkEquals(null, null));
    }
}
