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
     * Verifies that dumping a 17-byte array (all zeros except byte[5] = 0x7F)
     * from offset 0 produces the correct two-line hex dump output.
     *
     * 0x7F is the DEL character (ASCII 127), which falls outside the printable
     * range [0x20, 0x7F) and is therefore rendered as '.' in the ASCII column.
     *
     * Line 1 covers bytes 0–15 (offset 0x00000000).
     * Line 2 covers byte 16  (offset 0x00000010), padded to full line width.
     * Total output: 133 characters.
     */
    @Test(timeout = 4000)
    public void test0() throws Throwable {
        // Build a 17-byte payload: all zeros, with 0x7F (DEL) at position 5.
        byte[] data = new byte[17];
        data[5] = (byte) 0x7F;

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        HexDump.dump(data, 0L, (OutputStream) outputStream, 0);

        // Two lines: one for the first 16 bytes and one for the remaining byte.
        String expectedHexDump =
                "00000000 00 00 00 00 00 7F 00 00 00 00 00 00 00 00 00 00 ................\n" +
                "00000010 00                                              .\n";

        assertEquals(133, outputStream.size());
        assertEquals(expectedHexDump, outputStream.toString());
    }
}
