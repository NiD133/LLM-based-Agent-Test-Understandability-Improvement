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
public class CharRange_ESTest_test01 extends CharRange_ESTest_scaffolding {

    private static final char SINGLE_CHARACTER_RANGE_VALUE = 'y';

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        CharRange expectedRange = CharRange.is(SINGLE_CHARACTER_RANGE_VALUE);
        CharRange actualRange = CharRange.is(SINGLE_CHARACTER_RANGE_VALUE);

        boolean rangesAreEqual = actualRange.equals(expectedRange);

        assertEquals(SINGLE_CHARACTER_RANGE_VALUE, actualRange.getEnd());
        assertEquals(SINGLE_CHARACTER_RANGE_VALUE, actualRange.getStart());
        assertFalse(actualRange.isNegated());
        assertTrue(rangesAreEqual);
    }
}
