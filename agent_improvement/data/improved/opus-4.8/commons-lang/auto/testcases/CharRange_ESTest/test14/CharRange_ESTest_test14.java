package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharRange_ESTest_test14 extends CharRange_ESTest_scaffolding {

    /**
     * A negated single-character range ("everything except '1'") does not
     * entirely contain a negated multi-character range. {@code isNotIn('T', 'J')}
     * stores its endpoints in ascending order, so the range spans 'J' to 'T'.
     */
    @Test(timeout = 4000)
    public void test14() throws Throwable {
        CharRange notOne = CharRange.isNot('1');
        CharRange notJtoT = CharRange.isNotIn('T', 'J');

        boolean notOneContainsNotJtoT = notOne.contains(notJtoT);

        assertFalse(notOneContainsNotJtoT);

        // isNotIn reorders the endpoints so that start <= end.
        assertEquals('J', notJtoT.getStart());
        assertEquals('T', notJtoT.getEnd());

        // A single-character range has equal start and end, and is negated.
        assertEquals('1', notOne.getStart());
        assertEquals('1', notOne.getEnd());
        assertTrue(notOne.isNegated());
    }
}
