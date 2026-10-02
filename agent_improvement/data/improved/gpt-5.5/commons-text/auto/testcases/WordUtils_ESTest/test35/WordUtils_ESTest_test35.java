package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class WordUtils_ESTest_test35 extends WordUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test35() throws Throwable {
        final String textToAbbreviate = "LZzA7+<X<Kkh0y";
        final int lowerLimit = 0;
        final int invalidUpperLimit = -1881;
        final String abbreviationSuffix = "?AnDuL6yPz+";

        try {
            WordUtils.abbreviate(textToAbbreviate, lowerLimit, invalidUpperLimit, abbreviationSuffix);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
