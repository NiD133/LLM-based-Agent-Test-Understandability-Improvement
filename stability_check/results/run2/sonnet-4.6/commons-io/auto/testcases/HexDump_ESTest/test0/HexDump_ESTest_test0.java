package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.io.PipedWriter;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.io.MockFile;
import org.evosuite.runtime.mock.java.io.MockFileWriter;
import org.evosuite.runtime.mock.java.io.MockPrintWriter;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class HexDump_ESTest_test0 extends HexDump_ESTest_scaffolding {

    /**
     * Verifies that HexDump.dump correctly formats a 17-byte array into two
     * lines of hex output. The array is all zeros except byte[5] = 0x7F (127),
     * which should appear as "7F" in the hex representation and "." in the
     * ASCII column (since 0x7F is the DEL character, not printable).
     *
     * Expected output (two lines):
     *   Line 1: offset 0x00000000, bytes 0–15 (16 bytes including the 0x7F at position 5)
     *   Line 2: offset 0x00000010, byte 16 (the 17th byte, value 0x00)
     */
    @Test(timeout = 4000)
    public void test0() throws Throwable {
        // Arrange: 17-byte array with a single non-zero value at index 5
        byte[] inputData = new byte[17];
        inputData[5] = (byte) 127; // 0x7F — will show as "7F" in hex, "." in ASCII

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        // Act: dump the entire array from the start, with a file offset of 0
        HexDump.dump(inputData, 0L, (OutputStream) outputStream, 0);

        // Assert: total byte count of the formatted output
        assertEquals(133, outputStream.size());

        // Assert: exact formatted content — two lines of 16-byte hex rows
        String expectedOutput =
            "00000000 00 00 00 00 00 7F 00 00 00 00 00 00 00 00 00 00 ................\n"
            + "00000010 00                                              .\n";
        assertEquals(expectedOutput, outputStream.toString());
    }
}
