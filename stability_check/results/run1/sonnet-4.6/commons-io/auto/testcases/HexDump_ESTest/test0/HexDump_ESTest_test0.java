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
     * Verifies that HexDump.dump correctly formats a 17-byte array where byte
     * at index 5 is set to 0x7F (127). The dump starts at logical offset 0
     * and begins reading from array index 0.
     *
     * Expected output: two lines — the first covering bytes 0–15, the second
     * covering the remaining single byte (index 16).
     */
    @Test(timeout = 4000)
    public void test_dumpProducesTwoLineHexOutput_whenArrayHas17BytesAndByte5Is0x7F() throws Throwable {
        // 17-byte array: all zeros except index 5 which is 0x7F (127)
        byte[] inputBytes = new byte[17];
        inputBytes[5] = (byte) 127;

        ByteArrayOutputStream capturedOutput = new ByteArrayOutputStream();

        // Dump from logical file offset 0, reading the entire array starting at index 0
        HexDump.dump(inputBytes, 0L, (OutputStream) capturedOutput, 0);

        // Two lines: 16 bytes on the first line, 1 byte on the second line
        // Each line: 8-char offset + space + 16 hex pairs + spaces + 16 ASCII chars + newline
        String expectedHexDump =
                "00000000 00 00 00 00 00 7F 00 00 00 00 00 00 00 00 00 00 ................\n"
              + "00000010 00                                              .\n";

        assertEquals(133, capturedOutput.size());
        assertEquals(expectedHexDump, capturedOutput.toString());
    }
}
