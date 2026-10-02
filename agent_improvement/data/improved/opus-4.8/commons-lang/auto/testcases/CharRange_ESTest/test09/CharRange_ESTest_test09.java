package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharRange_ESTest_test09 extends CharRange_ESTest_scaffolding {

    /**
     * A non-negated range '2'-'U' does not contain a negated single-character
     * range (everything except '2'). Per {@link CharRange#contains(CharRange)},
     * a non-negated range can only contain a negated range when it spans every
     * possible character (start == 0 and end == Character.MAX_VALUE), which the
     * '2'-'U' range does not.
     */
    @Test(timeout = 4000)
    public void testNonNegatedRangeDoesNotContainNegatedRange() throws Throwable {
        // Negated range: every character except '2'.
        CharRange everythingExcept2 = CharRange.isNot('2');
        // Plain inclusive range covering '2' through 'U'.
        CharRange range2ToU = CharRange.isIn('2', 'U');

        boolean contains = range2ToU.contains(everythingExcept2);

        assertFalse(contains);
        // Endpoints are unchanged by the contains() check.
        assertEquals('2', range2ToU.getStart());
        assertEquals('U', range2ToU.getEnd());
        // A single-character negated range has equal start and end.
        assertEquals('2', everythingExcept2.getStart());
        assertEquals('2', everythingExcept2.getEnd());
    }
}
