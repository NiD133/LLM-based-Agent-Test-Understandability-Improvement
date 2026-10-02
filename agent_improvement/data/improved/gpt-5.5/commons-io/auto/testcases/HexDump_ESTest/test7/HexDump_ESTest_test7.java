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
    public void dumpToAppendableLeavesInputBytesUnchanged() throws Throwable {
        byte[] inputBytes = new byte[6];
        File tempFile = MockFile.createTempFile("file", "file");
        MockPrintWriter dumpOutput = new MockPrintWriter(tempFile);

        HexDump.dump(inputBytes, (Appendable) dumpOutput);

        byte[] unchangedZeroBytes = new byte[] {
            (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0
        };
        assertArrayEquals(unchangedZeroBytes, inputBytes);
    }
}
