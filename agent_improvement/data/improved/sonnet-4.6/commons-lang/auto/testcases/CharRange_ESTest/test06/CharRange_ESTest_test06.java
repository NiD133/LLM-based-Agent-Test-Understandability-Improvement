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
public class CharRange_ESTest_test06 extends CharRange_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_equalsReturnsFalse_whenComparedToNonCharRangeObject() throws Throwable {
        // Create a single-character range for 'O'
        CharRange singleCharRange = CharRange.isIn('O', 'O');

        // Compare against a plain Object (not a CharRange)
        Object nonCharRangeObject = new Object();
        boolean isEqual = singleCharRange.equals(nonCharRangeObject);

        // The range should still span only 'O'
        assertEquals('O', singleCharRange.getStart());
        assertEquals('O', singleCharRange.getEnd());

        // Comparing to a non-CharRange object must return false
        assertFalse(isEqual);
    }
}
