package org.apache.commons.compress.utils;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.File;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.io.MockFile;
import org.evosuite.runtime.mock.java.io.MockPrintStream;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ByteUtils_ESTest_test03 extends ByteUtils_ESTest_scaffolding {

    /**
     * Writing a single-byte little-endian value through an {@link ByteUtils.OutputStreamByteConsumer}
     * backed by a file should write exactly one byte to that file.
     */
    @Test(timeout = 4000)
    public void toLittleEndianWritesSingleByteToUnderlyingFile() throws Throwable {
        // Create an output stream that writes to a temporary file.
        File targetFile = MockFile.createTempFile("suffixes", "");
        MockPrintStream outputStream = new MockPrintStream(targetFile);

        // Wrap the stream so toLittleEndian can feed bytes to it.
        ByteUtils.OutputStreamByteConsumer consumer =
            new ByteUtils.OutputStreamByteConsumer(outputStream);

        // Write the value 77 using a single byte (length = 1).
        long value = (byte) 77;
        int lengthInBytes = 1;
        ByteUtils.toLittleEndian((ByteUtils.ByteConsumer) consumer, value, lengthInBytes);

        // Exactly one byte should have reached the file.
        assertEquals(1L, targetFile.length());
    }
}
