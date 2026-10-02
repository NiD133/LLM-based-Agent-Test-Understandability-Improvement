package org.apache.commons.compress.harmony.pack200;

import org.junit.Test;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

/**
 * Tests for {@link CodecEncoding}, which maps Pack200 meta-encoding byte values
 * to the appropriate codec implementation.
 */
@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CodecEncoding_ESTest_test19 extends CodecEncoding_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void testDefaultConstructorSucceeds() {
        // CodecEncoding is a utility class; the constructor is deprecated but must still be instantiable
        CodecEncoding codecEncoding = new CodecEncoding();
    }
}
