package org.apache.commons.text;

import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class WordUtils_ESTest_test08 extends WordUtils_ESTest_scaffolding {

    /**
     * A null input String must yield a null result, regardless of the
     * delimiters supplied (here a single-element delimiter array).
     */
    @Test(timeout = 4000)
    public void uncapitalizeReturnsNullForNullInput() throws Throwable {
        char[] delimiters = new char[1];

        String result = WordUtils.uncapitalize((String) null, delimiters);

        assertNull(result);
    }
}
