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
public class CharRange_ESTest_test28 extends CharRange_ESTest_scaffolding {

    /**
     * Tests that a single-character range created via isIn() correctly stores its
     * start and end as the same character, reports itself as non-negated, and that
     * calling hashCode() on it does not throw an exception.
     */
    @Test(timeout = 4000)
    public void test_isIn_singleChar_hashCodeDoesNotThrowAndStateIsCorrect() throws Throwable {
        CharRange singleCharRange = CharRange.isIn('O', 'O');

        // Verify hashCode() completes without exception (return value intentionally unused)
        singleCharRange.hashCode();

        assertEquals('O', singleCharRange.getStart());
        assertEquals('O', singleCharRange.getEnd());
        assertFalse(singleCharRange.isNegated());
    }
}
