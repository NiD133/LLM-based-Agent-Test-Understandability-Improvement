package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class WordUtils_ESTest_test37 extends WordUtils_ESTest_scaffolding {

    /**
     * Capitalizing an empty string should return an empty string unchanged,
     * since there are no words to capitalize.
     */
    @Test(timeout = 4000)
    public void capitalize_emptyString_returnsEmptyString() throws Throwable {
        String result = WordUtils.capitalize("");

        assertEquals("", result);
    }
}
