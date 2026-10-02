package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.io.MockFile;
import org.evosuite.runtime.mock.java.io.MockFileWriter;
import org.evosuite.runtime.mock.java.io.MockPrintWriter;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class HexDump_ESTest_test0 extends HexDump_ESTest_scaffolding {

    private static final int DUMP_INPUT_LENGTH = 17;
    private static final int PRINTABLE_BYTE_INDEX = 5;
    private static final byte DELETE_CHARACTER = (byte) 127;
    private static final long DISPLAY_OFFSET = 0L;
    private static final int START_INDEX = 0;
    private static final int EXPECTED_DUMP_SIZE = 133;

    private static final String EXPECTED_HEX_DUMP =
            "00000000 00 00 00 00 00 7F 00 00 00 00 00 00 00 00 00 00 ................\n"
                    + "00000010 00                                              .\n";

    @Test(timeout = 4000)
    public void test0() throws Throwable {
        byte[] bytesToDump = new byte[DUMP_INPUT_LENGTH];
        bytesToDump[PRINTABLE_BYTE_INDEX] = DELETE_CHARACTER;
        ByteArrayOutputStream dumpOutput = new ByteArrayOutputStream();

        HexDump.dump(bytesToDump, DISPLAY_OFFSET, (OutputStream) dumpOutput, START_INDEX);

        assertEquals(EXPECTED_DUMP_SIZE, dumpOutput.size());
        assertEquals(EXPECTED_HEX_DUMP, dumpOutput.toString());
    }
}
