package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharRange_ESTest_test27 extends CharRange_ESTest_scaffolding {

    /**
     * A single-character range created with {@link CharRange#isIn(char, char)}
     * should report that character as both its start and end, and should not
     * be negated.
     */
    @Test(timeout = 4000)
    public void isInWithSameStartAndEndCreatesSingleCharacterRange() throws Throwable {
        CharRange singleCharRange = CharRange.isIn('O', 'O');

        assertEquals("start should be the given character", 'O', singleCharRange.getStart());
        assertEquals("end should be the given character", 'O', singleCharRange.getEnd());
        assertFalse("range built with isIn must not be negated", singleCharRange.isNegated());
    }
}
