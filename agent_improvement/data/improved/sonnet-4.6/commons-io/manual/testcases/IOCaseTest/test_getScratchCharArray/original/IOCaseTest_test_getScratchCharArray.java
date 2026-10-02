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

public class IOCaseTest_test_getScratchCharArray {

    private static final boolean WINDOWS = File.separatorChar == '\\';

    private void assert0(final byte[] arr) {
        for (final byte e : arr) {
            assertEquals(0, e);
        }
    }

    private void assert0(final char[] arr) {
        for (final char e : arr) {
            assertEquals(0, e);
        }
    }

    @Test
    void test_getScratchCharArray() {
        final char[] array;
        try (ScratchChars scratch = IOUtils.ScratchChars.get()) {
            array = scratch.array();
            assert0(array);
            Arrays.fill(array, (char) 1);
            // Get another array, while the first is still in use
            // The test doesn't need the try here but that's the pattern.
            try (ScratchChars scratch2 = IOUtils.ScratchChars.get()) {
                final char[] array2 = scratch2.array();
                assert0(array2);
                assertNotSame(array, array2);
            }
        }
        // The first array should be reset and reusable
        try (ScratchChars scratch = IOUtils.ScratchChars.get()) {
            final char[] array3 = scratch.array();
            assert0(array3);
            assertSame(array, array3);
        }
    }
}
