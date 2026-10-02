package org.apache.commons.io;

import static org.junit.Assert.assertArrayEquals;

import java.io.File;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.io.MockFile;
import org.evosuite.runtime.mock.java.io.MockPrintWriter;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class HexDump_ESTest_test7 extends HexDump_ESTest_scaffolding {

    /**
     * Verifies that dumping a byte array to an Appendable does not modify the
     * source array: the six zero bytes remain unchanged after the dump.
     */
    @Test(timeout = 4000)
    public void dumpToAppendableLeavesInputArrayUnchanged() throws Throwable {
        byte[] inputBytes = new byte[6];

        File tempFile = MockFile.createTempFile("file", "file");
        MockPrintWriter writer = new MockPrintWriter(tempFile);
        HexDump.dump(inputBytes, (Appendable) writer);

        byte[] expectedBytes = new byte[] {
            (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0
        };
        assertArrayEquals(expectedBytes, inputBytes);
    }
}
