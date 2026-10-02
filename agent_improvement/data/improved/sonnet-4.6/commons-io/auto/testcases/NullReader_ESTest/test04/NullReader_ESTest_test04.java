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
public class NullReader_ESTest_test04 extends NullReader_ESTest_scaffolding {

    /**
     * Verifies that calling reset() on NullReader.INSTANCE without a prior mark()
     * throws IOException with the message "No position has been marked".
     */
    @Test(timeout = 4000)
    public void test04() throws Throwable {
        try {
            NullReader.INSTANCE.reset();
            fail("Expecting exception: IOException");
        } catch (IOException e) {
            // Expected: "No position has been marked"
            verifyException("org.apache.commons.io.input.NullReader", e);
        }
    }
}
