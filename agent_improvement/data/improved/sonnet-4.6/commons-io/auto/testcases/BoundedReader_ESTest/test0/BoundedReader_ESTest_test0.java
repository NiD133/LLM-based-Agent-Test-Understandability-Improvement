package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.StringReader;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BoundedReader_ESTest_test0 extends BoundedReader_ESTest_scaffolding {

    /**
     * Verifies that reading one character into an offset buffer position succeeds
     * and returns the correct number of characters read (1), even when the
     * BoundedReader limit is set to exactly 1.
     */
    @Test(timeout = 4000)
    public void test_readSingleCharIntoBufferAtOffset_returnsOne() throws Throwable {
        char[] buffer = new char[14];
        StringReader source = new StringReader("KnLjCdWGnB@(}p3qC");
        BoundedReader boundedReader = new BoundedReader(source, 1);

        int charsRead = boundedReader.read(buffer, 1, 1);

        assertEquals(1, charsRead);
    }
}
