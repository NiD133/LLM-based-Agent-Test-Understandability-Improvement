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

    private static final String EXPECTED_DUMP =
            "00000000 48 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 H...............\n"
                    + "00000010 00                                              .\n";

    @Test(timeout = 4000)
    public void test1() throws Throwable {
        byte[] bytesToDump = new byte[17];
        bytesToDump[0] = (byte) 72;
        ByteArrayOutputStream dumpOutput = new ByteArrayOutputStream();

        HexDump.dump(bytesToDump, 0L, (OutputStream) dumpOutput, 0);

        assertEquals(EXPECTED_DUMP, dumpOutput.toString());
        assertEquals(133, dumpOutput.size());
    }
}
