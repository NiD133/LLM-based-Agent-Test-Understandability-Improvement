package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class NullInputStream_ESTest_test18 extends NullInputStream_ESTest_scaffolding {

    /**
     * The default constructor builds a stream that supports marking,
     * so {@link NullInputStream#markSupported()} should report {@code true}.
     */
    @Test(timeout = 4000)
    public void markSupportedReturnsTrueForDefaultStream() throws Throwable {
        NullInputStream defaultStream = new NullInputStream();

        boolean markIsSupported = defaultStream.markSupported();

        assertTrue(markIsSupported);
    }
}
