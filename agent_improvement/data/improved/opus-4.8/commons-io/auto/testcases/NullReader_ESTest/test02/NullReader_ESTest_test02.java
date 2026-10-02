package org.apache.commons.io.input;

import static org.junit.Assert.fail;
import static org.evosuite.runtime.EvoAssertions.verifyException;

import java.io.IOException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class NullReader_ESTest_test02 extends NullReader_ESTest_scaffolding {

    /**
     * Marking with a negative read limit makes any later reset() invalid:
     * reset() throws an IOException because the current position (0) has
     * already "passed" the negative read limit relative to the marked position.
     */
    @Test(timeout = 4000)
    public void resetAfterMarkWithNegativeReadLimitThrowsIOException() throws Throwable {
        NullReader nullReader = new NullReader();
        int negativeReadLimit = -2081;

        nullReader.mark(negativeReadLimit);

        try {
            nullReader.reset();
            fail("Expected an IOException: marked position is no longer valid past the read limit");
        } catch (IOException e) {
            // Message: "Marked position [0] is no longer valid - passed the read limit [-2081]"
            verifyException("org.apache.commons.io.input.NullReader", e);
        }
    }
}
