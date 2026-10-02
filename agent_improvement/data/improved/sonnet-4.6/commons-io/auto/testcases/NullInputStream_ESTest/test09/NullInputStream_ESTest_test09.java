package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.EOFException;
import java.io.IOException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class NullInputStream_ESTest_test09 extends NullInputStream_ESTest_scaffolding {

    /**
     * Reading from the singleton INSTANCE (a zero-size stream) into a byte array
     * should immediately signal end-of-file by returning -1.
     */
    @Test(timeout = 4000)
    public void test_readFromZeroSizeInstanceReturnsEOF() throws Throwable {
        NullInputStream emptyStream = NullInputStream.INSTANCE;
        byte[] buffer = new byte[1];

        int bytesRead = emptyStream.read(buffer);

        assertEquals(-1, bytesRead);
    }
}
