package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharRange_ESTest_test09 extends CharRange_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test09() throws Throwable {
        // notTwoRange = all characters except '2' (negated single-char range)
        CharRange notTwoRange = CharRange.isNot('2');
        // twoToURange = characters from '2' to 'U' inclusive (non-negated range)
        CharRange twoToURange = CharRange.isIn('2', 'U');

        // A bounded non-negated range cannot contain a negated range unless it spans all chars
        boolean twoToUContainsNotTwo = twoToURange.contains(notTwoRange);
        assertFalse(twoToUContainsNotTwo);

        // Verify the boundary characters of each range are as expected
        assertEquals('2', twoToURange.getStart());
        assertEquals('U', twoToURange.getEnd());
        assertEquals('2', notTwoRange.getStart());
        assertEquals('2', notTwoRange.getEnd());
    }
}
