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
public class HexDump_ESTest_test1 extends HexDump_ESTest_scaffolding {

    /**
     * Dumps a 17-byte array whose first byte is 'H' (0x48) and the rest zero.
     * Because the array spans more than one 16-byte row, the formatted output
     * is laid out over two lines: the first row holds all 16 hex pairs plus the
     * ASCII gutter ("H" followed by dots for the non-printable zero bytes), and
     * the second row holds the single remaining byte.
     */
    @Test(timeout = 4000)
    public void test1() throws Throwable {
        byte[] data = new byte[17];
        data[0] = (byte) 'H';

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        HexDump.dump(data, 0L, (OutputStream) output, 0);

        String expectedDump =
                "00000000 48 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 H...............\n"
              + "00000010 00                                              .\n";
        assertEquals(expectedDump, output.toString());
        assertEquals(133, output.size());
    }
}
