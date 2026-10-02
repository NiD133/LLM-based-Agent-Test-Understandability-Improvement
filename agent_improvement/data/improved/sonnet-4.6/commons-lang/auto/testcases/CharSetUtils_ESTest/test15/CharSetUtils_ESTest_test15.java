package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharSetUtils_ESTest_test15 extends CharSetUtils_ESTest_scaffolding {

    /**
     * Verifies that squeeze() returns the original string unchanged when the
     * character set array contains only null elements (treated as an empty set).
     * Because no characters are specified for squeezing, repeated characters
     * in the input are left as-is.
     */
    @Test(timeout = 4000)
    public void test_squeeze_withAllNullCharSetElements_returnsInputUnchanged() throws Throwable {
        // A two-element array whose entries are both null — treated as an empty char set
        String[] nullElementSet = new String[2];

        String input = "Minimum abbreviation width with offset is %d";
        String result = CharSetUtils.squeeze(input, nullElementSet);

        // With an empty/null char set, squeeze performs no squeezing
        assertEquals(input, result);
    }
}
