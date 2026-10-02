package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.IOException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class NullInputStream_ESTest_test05 extends NullInputStream_ESTest_scaffolding {

    /**
     * Calling {@code reset()} before any position has been marked must fail with
     * an {@link IOException}, because the stream has no marked position to return to.
     */
    @Test(timeout = 4000)
    public void reset_withoutPriorMark_throwsIOException() throws Throwable {
        NullInputStream nullInputStream = NullInputStream.INSTANCE;

        try {
            nullInputStream.reset();
            fail("Expected an IOException because no position has been marked");
        } catch (IOException e) {
            // Expected: reset() reports "No position has been marked".
            verifyException("org.apache.commons.io.input.NullInputStream", e);
        }
    }
}
