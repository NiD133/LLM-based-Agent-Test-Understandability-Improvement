package org.apache.commons.text;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class WordUtils_ESTest_test36 extends WordUtils_ESTest_scaffolding {

    /**
     * The public no-argument constructor is provided only so that bean-style
     * tools can instantiate {@link WordUtils}; verify it produces an instance.
     */
    @Test(timeout = 4000)
    public void constructorCreatesInstance() throws Throwable {
        WordUtils wordUtils = new WordUtils();

        assertNotNull(wordUtils);
    }
}
