package org.apache.commons.compress.harmony.pack200;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CodecEncoding_ESTest_test19 extends CodecEncoding_ESTest_scaffolding {

    /**
     * Verifies that the (deprecated) no-argument constructor of {@link CodecEncoding}
     * can be invoked successfully and yields a usable instance.
     */
    @Test(timeout = 4000)
    public void constructorCreatesInstance() throws Throwable {
        CodecEncoding codecEncoding = new CodecEncoding();

        assertNotNull(codecEncoding);
    }
}
