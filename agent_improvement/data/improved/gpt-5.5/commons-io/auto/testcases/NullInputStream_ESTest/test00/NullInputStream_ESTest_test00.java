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

    @Test(timeout = 4000)
    public void resetWithoutMarkThrowsIOException() throws Throwable {
        NullInputStream streamWithSize = new NullInputStream(5480L);

        try {
            streamWithSize.INSTANCE.reset();
            fail("Expecting exception: IOException");
        } catch (IOException expectedException) {
            verifyException("org.apache.commons.io.input.NullInputStream", expectedException);
        }
    }
}
