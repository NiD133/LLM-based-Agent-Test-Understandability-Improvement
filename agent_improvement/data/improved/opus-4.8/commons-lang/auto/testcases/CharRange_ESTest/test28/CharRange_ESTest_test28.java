package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharRange_ESTest_test28 extends CharRange_ESTest_scaffolding {

    /**
     * A single-character range built with {@link CharRange#isIn(char, char)} where
     * start and end are the same character ('O') should expose that character as
     * both its start and end, and should not be negated. Calling hashCode() must
     * not disturb any of these properties.
     */
    @Test(timeout = 4000)
    public void singleCharacterRangeExposesItsBoundsAndIsNotNegated() throws Throwable {
        CharRange singleCharRange = CharRange.isIn('O', 'O');

        // hashCode() is read-only; invoking it must leave the range unchanged.
        singleCharRange.hashCode();

        assertEquals('O', singleCharRange.getStart());
        assertEquals('O', singleCharRange.getEnd());
        assertFalse(singleCharRange.isNegated());
    }
}
