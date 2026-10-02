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

    @Test(timeout = 4000)
    public void test0() throws Throwable {
        byte[] byteArray0 = new byte[17];
        byteArray0[5] = (byte) 127;
        ByteArrayOutputStream byteArrayOutputStream0 = new ByteArrayOutputStream();
        HexDump.dump(byteArray0, 0L, (OutputStream) byteArrayOutputStream0, 0);
        assertEquals(133, byteArrayOutputStream0.size());
        assertEquals("00000000 00 00 00 00 00 7F 00 00 00 00 00 00 00 00 00 00 ................\n00000010 00                                              .\n", byteArrayOutputStream0.toString());
    }
}
