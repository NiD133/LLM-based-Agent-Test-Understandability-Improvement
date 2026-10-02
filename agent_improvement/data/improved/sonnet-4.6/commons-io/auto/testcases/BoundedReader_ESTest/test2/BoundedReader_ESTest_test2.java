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
     * A BoundedReader constructed with a negative max-character limit (-1) is
     * immediately exhausted: charsRead (0) >= maxChars (-1) is true from the
     * start, so every read returns EOF regardless of what the underlying reader
     * would supply.
     */
    @Test(timeout = 4000)
    public void test2() throws Throwable {
        PipedReader source = new PipedReader();
        BoundedReader boundedReader = new BoundedReader(source, -1);

        char[] buffer = new char[14];
        int charsRead = boundedReader.read(buffer, 1, 1);

        assertEquals(-1, charsRead);
    }
}
