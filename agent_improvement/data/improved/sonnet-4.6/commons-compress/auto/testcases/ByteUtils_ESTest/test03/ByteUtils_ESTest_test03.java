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

    @Test(timeout = 4000)
    public void test03_writingSingleByteViaConsumerProducesOneBytInFile() throws Throwable {
        File tempFile = MockFile.createTempFile("suffixes", "");
        MockPrintStream printStream = new MockPrintStream(tempFile);
        ByteUtils.OutputStreamByteConsumer consumer = new ByteUtils.OutputStreamByteConsumer(printStream);

        // Write the value 77 as a 1-byte little-endian sequence
        ByteUtils.toLittleEndian((ByteUtils.ByteConsumer) consumer, 77L, 1);

        assertEquals(1L, tempFile.length());
    }
}
