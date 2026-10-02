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
public class CharRange_ESTest_test04 extends CharRange_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_negatedSingleCharRangeIsNotEqualToInclusiveRange() throws Throwable {
        // A negated single-char range covers everything except '2'
        CharRange rangeExcluding2 = CharRange.isNot('2');

        // An inclusive range covers characters from '2' to 'U'
        CharRange range2ToU = CharRange.isIn('2', 'U');

        // The two ranges differ in negation, so they must not be equal
        boolean rangesAreEqual = range2ToU.equals(rangeExcluding2);

        // The negated range is marked as negated and spans only '2'
        assertTrue(rangeExcluding2.isNegated());
        assertEquals('2', rangeExcluding2.getStart());
        assertEquals('2', rangeExcluding2.getEnd());

        // The inclusive range spans from '2' to 'U' and is not equal to the negated one
        assertEquals('2', range2ToU.getStart());
        assertEquals('U', range2ToU.getEnd());
        assertFalse(rangesAreEqual);
    }
}
