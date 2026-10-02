package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharRange_ESTest_test10 extends CharRange_ESTest_scaffolding {

    /** The NUL character (code point 0, the lowest possible char value). */
    private static final char NUL = (char) 0;

    @Test(timeout = 4000)
    public void test_singleNulCharRange_doesNotContain_negatedNulCharRange() throws Throwable {
        // Negated range: matches every character EXCEPT NUL
        CharRange negatedNulRange = CharRange.isNotIn(NUL, NUL);
        // Single-char range: matches only NUL
        CharRange singleNulRange = CharRange.is(NUL);

        // A range covering only NUL cannot contain a negated range that excludes NUL
        boolean singleNulContainsNegated = singleNulRange.contains(negatedNulRange);

        assertEquals(NUL, singleNulRange.getStart());
        assertFalse(singleNulContainsNegated);
        assertEquals(NUL, negatedNulRange.getStart());
        assertEquals(NUL, singleNulRange.getEnd());
        assertEquals(NUL, negatedNulRange.getEnd());
    }
}
