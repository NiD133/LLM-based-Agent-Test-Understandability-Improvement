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

    private static final int DATA_LENGTH = 17;
    private static final int DEL_BYTE_INDEX = 5;
    private static final byte DEL_BYTE = (byte) 127;
    private static final long DISPLAY_OFFSET = 0L;
    private static final int START_INDEX = 0;
    private static final int EXPECTED_DUMP_SIZE = 133;
    private static final String EXPECTED_HEX_DUMP =
            "00000000 00 00 00 00 00 7F 00 00 00 00 00 00 00 00 00 00 ................\n"
                    + "00000010 00                                              .\n";

    @Test(timeout = 4000)
    public void test0() throws Throwable {
        byte[] data = new byte[DATA_LENGTH];
        data[DEL_BYTE_INDEX] = DEL_BYTE;
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        HexDump.dump(data, DISPLAY_OFFSET, (OutputStream) output, START_INDEX);

        assertEquals(EXPECTED_DUMP_SIZE, output.size());
        assertEquals(EXPECTED_HEX_DUMP, output.toString());
    }
}
