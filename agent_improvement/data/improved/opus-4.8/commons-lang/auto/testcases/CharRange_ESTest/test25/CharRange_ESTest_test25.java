package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharRange_ESTest_test25 extends CharRange_ESTest_scaffolding {

    /**
     * A negated single-character range built with {@link CharRange#isNot(char)}
     * collapses start and end to the given character and is flagged as negated.
     */
    @Test(timeout = 4000)
    public void isNot_createsNegatedSingleCharacterRange() throws Throwable {
        CharRange negatedRange = CharRange.isNot('2');

        assertEquals('2', negatedRange.getStart());
        assertEquals('2', negatedRange.getEnd());
        assertTrue(negatedRange.isNegated());
    }
}
