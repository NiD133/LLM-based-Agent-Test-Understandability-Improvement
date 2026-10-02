package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class HexDump_ESTest_test0 extends HexDump_ESTest_scaffolding {

    /**
     * Dumps a 17-byte array (all zero except byte 5 = 0x7F) starting at
     * offset 0 / index 0 and verifies the formatted hex-dump written to the
     * stream. The output spans two lines: 16 bytes on the first line and the
     * remaining single byte on the second.
     */
    @Test(timeout = 4000)
    public void test0() throws Throwable {
        byte[] data = new byte[17];
        data[5] = (byte) 127;
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        HexDump.dump(data, 0L, (OutputStream) output, 0);

        String expectedDump =
                "00000000 00 00 00 00 00 7F 00 00 00 00 00 00 00 00 00 00 ................\n"
                + "00000010 00                                              .\n";
        assertEquals(133, output.size());
        assertEquals(expectedDump, output.toString());
    }
}
