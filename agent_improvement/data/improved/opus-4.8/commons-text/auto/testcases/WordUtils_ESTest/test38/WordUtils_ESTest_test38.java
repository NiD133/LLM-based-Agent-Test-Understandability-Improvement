package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class WordUtils_ESTest_test38 extends WordUtils_ESTest_scaffolding {

    /**
     * capitalizeFully should return null when given a null input String,
     * as documented by the method's null-handling contract.
     */
    @Test(timeout = 4000)
    public void capitalizeFully_withNullInput_returnsNull() throws Throwable {
        String result = WordUtils.capitalizeFully((String) null);

        assertNull(result);
    }
}
