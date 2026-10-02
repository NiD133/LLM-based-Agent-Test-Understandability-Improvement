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
public class CharRange_ESTest_test05 extends CharRange_ESTest_scaffolding {

    /**
     * Verifies that a negated single-character range created with isNot('|'):
     * - is reflexively equal to itself
     * - reports isNegated() == true
     * - has both start and end set to the single character '|'
     */
    @Test(timeout = 4000)
    public void test_isNot_singleChar_reflexiveEquality_andProperties() throws Throwable {
        // Create a negated range that matches everything except '|'
        CharRange negatedPipeRange = CharRange.isNot('|');

        // A range must equal itself (reflexive equality)
        boolean isEqualToItself = negatedPipeRange.equals(negatedPipeRange);
        assertTrue(isEqualToItself);

        // isNot produces a negated range
        assertTrue(negatedPipeRange.isNegated());

        // For a single-character range, start and end are both that character
        assertEquals('|', negatedPipeRange.getStart());
        assertEquals('|', negatedPipeRange.getEnd());
    }
}
