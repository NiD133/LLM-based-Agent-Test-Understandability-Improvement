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

    @Test(timeout = 4000)
    public void test1() throws Throwable {
        byte[] byteArray0 = new byte[17];
        byteArray0[0] = (byte) 72;
        ByteArrayOutputStream byteArrayOutputStream0 = new ByteArrayOutputStream();
        HexDump.dump(byteArray0, 0L, (OutputStream) byteArrayOutputStream0, 0);
        assertEquals("00000000 48 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 H...............\n00000010 00                                              .\n", byteArrayOutputStream0.toString());
        assertEquals(133, byteArrayOutputStream0.size());
    }
}
