package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import java.util.function.Consumer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharRange_ESTest_test16 extends CharRange_ESTest_scaffolding {

    /**
     * A negated single-character range (everything except '1') must contain itself,
     * because the excluded region of the range is a subset of its own excluded region.
     *
     * After construction via isNot, start and end are both '1' and the range is negated.
     */
    @Test(timeout = 4000)
    public void test_negatedRangeContainsItself() throws Throwable {
        // Create a negated range: all characters except '1'
        CharRange everythingExceptDigitOne = CharRange.isNot('1');

        // A negated range always contains itself (its excluded region is equal to its own)
        boolean containsItself = everythingExceptDigitOne.contains(everythingExceptDigitOne);

        // The range covers exactly '1' as both start and end, and is negated
        assertEquals('1', everythingExceptDigitOne.getStart());
        assertEquals('1', everythingExceptDigitOne.getEnd());
        assertTrue(everythingExceptDigitOne.isNegated());

        // A negated range contains itself
        assertTrue(containsItself);
    }
}
