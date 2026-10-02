package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharRange_ESTest_test03 extends CharRange_ESTest_scaffolding {

    /**
     * The DEL control character (code point 127), used here as the high end of
     * both ranges. Because its value (127) is greater than 'Y' (89) and 'V' (86),
     * the CharRange factory methods swap the supplied endpoints so that
     * {@code start <= end}.
     */
    private static final char DEL = '';

    /**
     * Two CharRanges that share an end character but differ in start character
     * and in negation must not be considered equal.
     */
    @Test(timeout = 4000)
    public void equals_returnsFalse_forRangesThatDifferInStartAndNegation() throws Throwable {
        // Negated range: endpoints are passed reversed (DEL > 'Y'), so they get
        // swapped to start='Y', end=DEL.
        CharRange negatedRange = CharRange.isNotIn(DEL, 'Y');
        assertEquals('Y', negatedRange.getStart());
        assertEquals(DEL, negatedRange.getEnd());

        // Plain range: endpoints are passed reversed (DEL > 'V'), so they get
        // swapped to start='V', end=DEL.
        CharRange plainRange = CharRange.isIn(DEL, 'V');
        assertEquals('V', plainRange.getStart());
        assertEquals(DEL, plainRange.getEnd());

        // The ranges differ in start character ('V' vs 'Y') and negation
        // (false vs true), so they are not equal.
        assertFalse(plainRange.equals(negatedRange));
    }
}
