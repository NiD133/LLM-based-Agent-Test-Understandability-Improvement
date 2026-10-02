package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringUtils_ESTest_test11 extends StringUtils_ESTest_scaffolding {

    /**
     * Verifies that {@link StringUtils#equals(CharSequence, CharSequence)} returns
     * {@code false} when comparing two CharSequences of different content and length
     * (an empty string versus a non-empty string).
     */
    @Test(timeout = 4000)
    public void equalsReturnsFalseForDifferentStrings() throws Throwable {
        CharSequence emptyString = "";
        CharSequence nonEmptyString = "[NQ.38g~9u=YGOxnW";

        boolean areEqual = StringUtils.equals(emptyString, nonEmptyString);

        assertFalse(areEqual);
    }
}
