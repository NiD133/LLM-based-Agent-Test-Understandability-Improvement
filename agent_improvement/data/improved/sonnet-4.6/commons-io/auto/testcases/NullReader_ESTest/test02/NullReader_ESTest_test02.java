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
public class NullReader_ESTest_test02 extends NullReader_ESTest_scaffolding {

    /**
     * A negative readLimit passed to mark() makes the marked position immediately
     * invalid, because position (0) > mark (0) + readLimit (-2081). Calling reset()
     * after such a mark must throw IOException.
     */
    @Test(timeout = 4000)
    public void testResetThrowsIOExceptionWhenMarkedWithNegativeReadLimit() throws Throwable {
        NullReader reader = new NullReader();
        reader.mark(-2081);
        try {
            reader.reset();
            fail("Expecting exception: IOException");
        } catch (IOException e) {
            //
            // Marked position [0] is no longer valid - passed the read limit [-2081]
            //
            verifyException("org.apache.commons.io.input.NullReader", e);
        }
    }
}
