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
public class HexDump_ESTest_test4 extends HexDump_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test4() throws Throwable {
        byte[] byteArray0 = new byte[17];
        MockFile mockFile0 = new MockFile("org.apache.commons.io.filefilter.CanExecuteFileFilter", "org.apache.commons.io.filefilter.CanExecuteFileFilter");
        MockFileWriter mockFileWriter0 = new MockFileWriter(mockFile0, true);
        try {
            HexDump.dump(byteArray0, (long) (byte) 127, (Appendable) mockFileWriter0, (int) (byte) 127, (-3384));
            fail("Expecting exception: ArrayIndexOutOfBoundsException");
        } catch (ArrayIndexOutOfBoundsException e) {
            //
            // illegal index: 127 into array of length 17
            //
            verifyException("org.apache.commons.io.HexDump", e);
        }
    }
}
