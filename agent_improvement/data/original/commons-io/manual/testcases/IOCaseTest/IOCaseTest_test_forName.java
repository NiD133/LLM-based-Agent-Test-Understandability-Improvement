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

public class IOCaseTest_test_forName {

    private static final boolean WINDOWS = File.separatorChar == '\\';

    @Test
    void test_forName() {
        assertEquals(IOCase.SENSITIVE, IOCase.forName("Sensitive"));
        assertEquals(IOCase.INSENSITIVE, IOCase.forName("Insensitive"));
        assertEquals(IOCase.SYSTEM, IOCase.forName("System"));
        assertThrows(IllegalArgumentException.class, () -> IOCase.forName("Blah"));
        assertThrows(IllegalArgumentException.class, () -> IOCase.forName(null));
    }
}
