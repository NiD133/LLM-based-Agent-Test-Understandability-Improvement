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
     * Verifies that dumping a 17-byte array (all zero except one byte set to
     * 0x7F at position 5) produces the expected hex-dump text. Because the data
     * spans more than 16 bytes, the output wraps onto a second line.
     */
    @Test(timeout = 4000)
    public void dumpSeventeenBytesWrapsOntoSecondLine() throws Throwable {
        // Given: 17 bytes, all zero except byte 5 which is 0x7F (127, non-printable).
        byte[] data = new byte[17];
        data[5] = (byte) 127;
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        // When: dumping the whole array starting at offset 0 and index 0.
        HexDump.dump(data, 0L, (OutputStream) output, 0);

        // Then: the dump contains a full 16-byte first line and a one-byte second line.
        String expectedDump =
                "00000000 00 00 00 00 00 7F 00 00 00 00 00 00 00 00 00 00 ................\n"
              + "00000010 00                                              .\n";
        assertEquals(133, output.size());
        assertEquals(expectedDump, output.toString());
    }
}
