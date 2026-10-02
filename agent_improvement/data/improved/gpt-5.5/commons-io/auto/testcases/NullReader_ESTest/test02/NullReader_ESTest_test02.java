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

    private static final int EXPIRED_READ_LIMIT = -2081;

    @Test(timeout = 4000)
    public void test02() throws Throwable {
        NullReader reader = new NullReader();
        reader.mark(EXPIRED_READ_LIMIT);

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
