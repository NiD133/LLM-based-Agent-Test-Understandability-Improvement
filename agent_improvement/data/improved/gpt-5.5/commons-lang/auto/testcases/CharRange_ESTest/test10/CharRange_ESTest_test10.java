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
public class CharRange_ESTest_test10 extends CharRange_ESTest_scaffolding {

    private static final char NUL = '\u0000';

    @Test(timeout = 4000)
    public void test10() throws Throwable {
        CharRange allExceptNul = CharRange.isNotIn(NUL, NUL);
        CharRange onlyNul = CharRange.is(NUL);

        boolean onlyNulContainsAllExceptNul = onlyNul.contains(allExceptNul);

        assertEquals(NUL, onlyNul.getStart());
        assertFalse(onlyNulContainsAllExceptNul);
        assertEquals(NUL, allExceptNul.getStart());
        assertEquals(NUL, onlyNul.getEnd());
        assertEquals(NUL, allExceptNul.getEnd());
    }
}
