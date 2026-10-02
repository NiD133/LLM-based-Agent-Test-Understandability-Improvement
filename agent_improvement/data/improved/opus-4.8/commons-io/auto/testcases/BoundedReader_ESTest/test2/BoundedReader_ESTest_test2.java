package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.PipedReader;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BoundedReader_ESTest_test2 extends BoundedReader_ESTest_scaffolding {

    /**
     * When the maximum number of characters allowed from the target reader is negative,
     * the BoundedReader is already "over budget" before reading anything, so a bulk read
     * should report end-of-stream (-1) immediately without touching the underlying reader.
     */
    @Test(timeout = 4000)
    public void readReturnsEofWhenMaxCharsIsNegative() throws Throwable {
        final int negativeMaxChars = -1;
        BoundedReader boundedReader = new BoundedReader(new PipedReader(), negativeMaxChars);

        char[] buffer = new char[14];
        int charsRead = boundedReader.read(buffer, 1, 1);

        assertEquals("Expected EOF since no characters may be read", -1, charsRead);
    }
}
