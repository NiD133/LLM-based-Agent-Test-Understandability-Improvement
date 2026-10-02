package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharRange_ESTest_test03 extends CharRange_ESTest_scaffolding {

    // This character has a code point greater than both 'V' (86) and 'Y' (89),
    // so CharRange's constructor will swap it to the end position in both ranges.
    private static final char HIGH_CHAR = '';

    @Test(timeout = 4000)
    public void test03_negatedAndNonNegatedRangesWithSameCharactersAreNotEqual() throws Throwable {
        // HIGH_CHAR > 'Y', so the constructor swaps them: stored as start='Y', end=HIGH_CHAR, negated=true
        CharRange negatedRange = CharRange.isNotIn(HIGH_CHAR, 'Y');

        // HIGH_CHAR > 'V', so the constructor swaps them: stored as start='V', end=HIGH_CHAR, negated=false
        CharRange inclusiveRange = CharRange.isIn(HIGH_CHAR, 'V');

        // A negated range and a non-negated range are never equal, even when characters overlap
        boolean equal = inclusiveRange.equals(negatedRange);
        assertFalse(equal);

        // Verify the inclusive range has its endpoints stored in ascending order
        assertEquals(HIGH_CHAR, inclusiveRange.getEnd());
        assertEquals('V', inclusiveRange.getStart());

        // Verify the negated range has its endpoints stored in ascending order
        assertEquals(HIGH_CHAR, negatedRange.getEnd());
        assertEquals('Y', negatedRange.getStart());
    }
}
