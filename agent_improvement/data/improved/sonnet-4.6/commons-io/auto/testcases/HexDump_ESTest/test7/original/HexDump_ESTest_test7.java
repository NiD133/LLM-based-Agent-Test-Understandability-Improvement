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
public class HexDump_ESTest_test7 extends HexDump_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test7() throws Throwable {
        byte[] byteArray0 = new byte[6];
        File file0 = MockFile.createTempFile("file", "file");
        MockPrintWriter mockPrintWriter0 = new MockPrintWriter(file0);
        HexDump.dump(byteArray0, (Appendable) mockPrintWriter0);
        assertArrayEquals(new byte[] { (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0 }, byteArray0);
    }
}
