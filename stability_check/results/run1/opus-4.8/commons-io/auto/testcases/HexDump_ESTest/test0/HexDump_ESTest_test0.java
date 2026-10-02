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
     * Verifies that dumping a 17-byte array (one byte more than a full 16-byte line)
     * produces two formatted lines: a full first line and a one-byte second line.
     * A single non-zero byte (0x7F) sits at index 5; because 0x7F is not a printable
     * ASCII character it is rendered as '.' in the character column.
     */
    @Test(timeout = 4000)
    public void testDumpArraySpanningTwoLines() throws Throwable {
        // Arrange: 17 zero bytes with 0x7F (DEL, non-printable) placed at index 5.
        byte[] data = new byte[17];
        data[5] = (byte) 127;
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        // Act: dump the whole array starting at file offset 0 and array index 0.
        HexDump.dump(data, 0L, (OutputStream) output, 0);

        // Assert: the formatted hex dump spans two lines of the expected content.
        String expectedDump =
                "00000000 00 00 00 00 00 7F 00 00 00 00 00 00 00 00 00 00 ................\n"
                + "00000010 00                                              .\n";
        assertEquals(expectedDump, output.toString());
        assertEquals(133, output.size());
    }
}
