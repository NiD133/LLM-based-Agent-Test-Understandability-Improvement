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
public class HexDump_ESTest_test1 extends HexDump_ESTest_scaffolding {

    // ASCII code 72 is 'H', hex 0x48
    private static final byte ASCII_H = (byte) 72;
    private static final long OFFSET_ZERO = 0L;
    private static final int START_INDEX = 0;

    // Expected hex dump output for a 17-byte array where only the first byte is 'H' (0x48):
    //   Line 1: offset 00000000, bytes 48 00 ... 00 (16 bytes), ASCII "H..............."
    //   Line 2: offset 00000010, byte 00 (1 byte), ASCII "."
    private static final String EXPECTED_HEX_DUMP =
            "00000000 48 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 H...............\n"
          + "00000010 00                                              .\n";
    private static final int EXPECTED_OUTPUT_SIZE = 133;

    @Test(timeout = 4000)
    public void test1() throws Throwable {
        // Build a 17-byte array: first byte is 'H', the rest are zero
        byte[] inputData = new byte[17];
        inputData[0] = ASCII_H;

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        HexDump.dump(inputData, OFFSET_ZERO, (OutputStream) outputStream, START_INDEX);

        assertEquals(EXPECTED_HEX_DUMP, outputStream.toString());
        assertEquals(EXPECTED_OUTPUT_SIZE, outputStream.size());
    }
}
