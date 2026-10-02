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

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        CharRange charRange0 = CharRange.is('y');
        CharRange charRange1 = CharRange.is('y');
        boolean boolean0 = charRange1.equals(charRange0);
        assertEquals('y', charRange1.getEnd());
        assertEquals('y', charRange1.getStart());
        assertFalse(charRange1.isNegated());
        assertTrue(boolean0);
    }
}
