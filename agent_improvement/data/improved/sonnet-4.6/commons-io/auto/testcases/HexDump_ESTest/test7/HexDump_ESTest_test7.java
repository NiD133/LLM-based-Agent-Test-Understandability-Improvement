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
        // Six zero bytes — the smallest non-trivial array whose hex dump fits in one line
        byte[] inputData = new byte[6];
        File tempFile = MockFile.createTempFile("file", "file");
        MockPrintWriter printWriter = new MockPrintWriter(tempFile);

        // Dump the hex representation to the writer; the source array must remain unchanged
        HexDump.dump(inputData, (Appendable) printWriter);

        byte[] expectedUnmodifiedData = { (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0 };
        assertArrayEquals(expectedUnmodifiedData, inputData);
    }
}
