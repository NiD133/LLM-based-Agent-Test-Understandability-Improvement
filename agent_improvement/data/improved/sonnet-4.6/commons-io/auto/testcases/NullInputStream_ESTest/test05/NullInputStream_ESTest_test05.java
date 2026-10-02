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
public class NullInputStream_ESTest_test05 extends NullInputStream_ESTest_scaffolding {

    /**
     * Verifies that calling reset() on NullInputStream.INSTANCE without a prior mark()
     * throws an IOException indicating no position has been marked.
     */
    @Test(timeout = 4000)
    public void test05() throws Throwable {
        NullInputStream instance = NullInputStream.INSTANCE;
        try {
            instance.reset();
            fail("Expecting exception: IOException");
        } catch (IOException e) {
            // reset() requires mark() to have been called first; mark starts at -1
            verifyException("org.apache.commons.io.input.NullInputStream", e);
        }
    }
}
