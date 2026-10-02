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
public class NullInputStream_ESTest_test00 extends NullInputStream_ESTest_scaffolding {

    /**
     * Calling {@code reset()} when no position has ever been marked must fail with
     * an {@link IOException}. The shared {@link NullInputStream#INSTANCE} starts with
     * no mark set, so resetting it triggers the "No position has been marked" error.
     */
    @Test(timeout = 4000)
    public void resetWithoutMarkThrowsIOException() throws Throwable {
        NullInputStream nullInputStream = new NullInputStream(5480L);

        try {
            nullInputStream.INSTANCE.reset();
            fail("Expected an IOException because no position has been marked");
        } catch (IOException expected) {
            // reset() throws IOException("No position has been marked") from NullInputStream
            verifyException("org.apache.commons.io.input.NullInputStream", expected);
        }
    }
}
