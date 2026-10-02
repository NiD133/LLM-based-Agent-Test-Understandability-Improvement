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
public class CharRange_ESTest_test27 extends CharRange_ESTest_scaffolding {

    /**
     * Verifies that a single-character range created with isIn('O', 'O')
     * has the same start and end character, and is not negated.
     */
    @Test(timeout = 4000)
    public void test_isIn_singleChar_startEqualsEnd() throws Throwable {
        // Create a range covering exactly one character: 'O'
        CharRange singleCharRange = CharRange.isIn('O', 'O');

        char start = singleCharRange.getStart();

        // The range should not be negated (it covers 'O', not everything except 'O')
        assertFalse(singleCharRange.isNegated());
        // Both start and end should be 'O'
        assertEquals('O', start);
        assertEquals('O', singleCharRange.getEnd());
    }
}
