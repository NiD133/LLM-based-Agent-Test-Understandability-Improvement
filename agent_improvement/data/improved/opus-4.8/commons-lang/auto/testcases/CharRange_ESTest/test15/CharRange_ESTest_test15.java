package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharRange_ESTest_test15 extends CharRange_ESTest_scaffolding {

    /**
     * A negated single-character range (everything except '1') should contain
     * any non-negated range whose characters all fall outside the excluded one.
     * Here the inner range covers only 'J', which is not '1', so it is contained.
     */
    @Test(timeout = 4000)
    public void negatedRangeContainsRangeOutsideExcludedChar() throws Throwable {
        CharRange everythingExceptOne = CharRange.isNot('1');
        CharRange onlyJ = CharRange.is('J');

        boolean contained = everythingExceptOne.contains(onlyJ);

        assertTrue(contained);
        assertEquals('1', everythingExceptOne.getStart());
        assertEquals('1', everythingExceptOne.getEnd());
        assertEquals('J', onlyJ.getStart());
        assertEquals('J', onlyJ.getEnd());
    }
}
