package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharRange_ESTest_test10 extends CharRange_ESTest_scaffolding {

    /** The character used for every endpoint in this test: U+0000 (the null character). */
    private static final char NULL_CHAR = '\\u0000';

    /**
     * A plain single-character range does not contain a negated range.
     * {@link CharRange#contains(CharRange)} only reports {@code true} for a
     * negated argument when the receiver spans the entire character space
     * (U+0000 to {@link Character#MAX_VALUE}), which this single-character
     * range does not.
     */
    @Test(timeout = 4000)
    public void singleCharRangeDoesNotContainNegatedRange() throws Throwable {
        CharRange negatedNullRange = CharRange.isNotIn(NULL_CHAR, NULL_CHAR);
        CharRange singleNullCharRange = CharRange.is(NULL_CHAR);

        boolean contains = singleNullCharRange.contains(negatedNullRange);

        assertFalse(contains);
        assertEquals(NULL_CHAR, singleNullCharRange.getStart());
        assertEquals(NULL_CHAR, singleNullCharRange.getEnd());
        assertEquals(NULL_CHAR, negatedNullRange.getStart());
        assertEquals(NULL_CHAR, negatedNullRange.getEnd());
    }
}
