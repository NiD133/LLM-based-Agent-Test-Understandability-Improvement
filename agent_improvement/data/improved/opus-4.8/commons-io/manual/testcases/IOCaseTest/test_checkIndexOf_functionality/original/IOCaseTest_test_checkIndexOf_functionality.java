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

public class IOCaseTest_test_checkIndexOf_functionality {

    private static final boolean WINDOWS = File.separatorChar == '\\';

    @Test
    void test_checkIndexOf_functionality() {
        // start
        assertEquals(0, IOCase.SENSITIVE.checkIndexOf("ABCDEFGHIJ", 0, "A"));
        assertEquals(-1, IOCase.SENSITIVE.checkIndexOf("ABCDEFGHIJ", 1, "A"));
        assertEquals(0, IOCase.SENSITIVE.checkIndexOf("ABCDEFGHIJ", 0, "AB"));
        assertEquals(-1, IOCase.SENSITIVE.checkIndexOf("ABCDEFGHIJ", 1, "AB"));
        assertEquals(0, IOCase.SENSITIVE.checkIndexOf("ABCDEFGHIJ", 0, "ABC"));
        assertEquals(-1, IOCase.SENSITIVE.checkIndexOf("ABCDEFGHIJ", 1, "ABC"));
        // middle
        assertEquals(3, IOCase.SENSITIVE.checkIndexOf("ABCDEFGHIJ", 0, "D"));
        assertEquals(3, IOCase.SENSITIVE.checkIndexOf("ABCDEFGHIJ", 3, "D"));
        assertEquals(-1, IOCase.SENSITIVE.checkIndexOf("ABCDEFGHIJ", 4, "D"));
        assertEquals(3, IOCase.SENSITIVE.checkIndexOf("ABCDEFGHIJ", 0, "DE"));
        assertEquals(3, IOCase.SENSITIVE.checkIndexOf("ABCDEFGHIJ", 3, "DE"));
        assertEquals(-1, IOCase.SENSITIVE.checkIndexOf("ABCDEFGHIJ", 4, "DE"));
        assertEquals(3, IOCase.SENSITIVE.checkIndexOf("ABCDEFGHIJ", 0, "DEF"));
        assertEquals(3, IOCase.SENSITIVE.checkIndexOf("ABCDEFGHIJ", 3, "DEF"));
        assertEquals(-1, IOCase.SENSITIVE.checkIndexOf("ABCDEFGHIJ", 4, "DEF"));
        // end
        assertEquals(9, IOCase.SENSITIVE.checkIndexOf("ABCDEFGHIJ", 0, "J"));
        assertEquals(9, IOCase.SENSITIVE.checkIndexOf("ABCDEFGHIJ", 8, "J"));
        assertEquals(9, IOCase.SENSITIVE.checkIndexOf("ABCDEFGHIJ", 9, "J"));
        assertEquals(8, IOCase.SENSITIVE.checkIndexOf("ABCDEFGHIJ", 0, "IJ"));
        assertEquals(8, IOCase.SENSITIVE.checkIndexOf("ABCDEFGHIJ", 8, "IJ"));
        assertEquals(-1, IOCase.SENSITIVE.checkIndexOf("ABCDEFGHIJ", 9, "IJ"));
        assertEquals(7, IOCase.SENSITIVE.checkIndexOf("ABCDEFGHIJ", 6, "HIJ"));
        assertEquals(7, IOCase.SENSITIVE.checkIndexOf("ABCDEFGHIJ", 7, "HIJ"));
        assertEquals(-1, IOCase.SENSITIVE.checkIndexOf("ABCDEFGHIJ", 8, "HIJ"));
        // not found
        assertEquals(-1, IOCase.SENSITIVE.checkIndexOf("ABCDEFGHIJ", 0, "DED"));
        // too long
        assertEquals(-1, IOCase.SENSITIVE.checkIndexOf("DEF", 0, "ABCDEFGHIJ"));
        assertEquals(-1, IOCase.SENSITIVE.checkIndexOf("ABC", 0, null));
        assertEquals(-1, IOCase.SENSITIVE.checkIndexOf(null, 0, "ABC"));
        assertEquals(-1, IOCase.SENSITIVE.checkIndexOf(null, 0, null));
    }
}
