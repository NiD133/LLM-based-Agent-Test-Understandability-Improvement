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
public class CharRange_ESTest_test24 extends CharRange_ESTest_scaffolding {

    private static final char RANGE_START = '\u0000';
    private static final char RANGE_END = '\u00DF';

    @Test(timeout = 4000)
    public void test24() throws Throwable {
        CharRange negatedRange = CharRange.isNotIn(RANGE_START, RANGE_END);

        negatedRange.spliterator();

        assertTrue(negatedRange.isNegated());
        assertEquals(RANGE_START, negatedRange.getStart());
        assertEquals(RANGE_END, negatedRange.getEnd());
    }
}
