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
public class CharRange_ESTest_test07 extends CharRange_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test07() throws Throwable {
        CharRange numericToUppercaseRange = CharRange.isIn('2', 'U');
        CharRange tildeRange = CharRange.is('~');

        boolean containsTildeRange = numericToUppercaseRange.contains(tildeRange);

        assertEquals('2', numericToUppercaseRange.getStart());
        assertEquals('U', numericToUppercaseRange.getEnd());
        assertEquals('~', tildeRange.getStart());
        assertEquals('~', tildeRange.getEnd());
        assertFalse(tildeRange.isNegated());
        assertFalse(containsTildeRange);
    }
}
