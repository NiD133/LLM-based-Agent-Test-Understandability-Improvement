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
     * Verifies that calling reset() on the shared INSTANCE without first calling mark()
     * throws an IOException because no position has been marked.
     */
    @Test(timeout = 4000)
    public void test00_resetWithoutMark_throwsIOException() throws Throwable {
        // Arrange: create any NullInputStream to access the static INSTANCE field
        NullInputStream anyInstance = new NullInputStream(5480L);

        // Act & Assert: reset() on INSTANCE (no mark set) must throw IOException
        try {
            anyInstance.INSTANCE.reset();
            fail("Expecting exception: IOException");
        } catch (IOException e) {
            //
            // No position has been marked
            //
            verifyException("org.apache.commons.io.input.NullInputStream", e);
        }
    }
}
