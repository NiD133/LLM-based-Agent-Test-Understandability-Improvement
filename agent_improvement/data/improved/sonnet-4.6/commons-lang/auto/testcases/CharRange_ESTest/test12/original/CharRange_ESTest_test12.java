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
public class CharRange_ESTest_test12 extends CharRange_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test12() throws Throwable {
        CharRange charRange0 = CharRange.isNotIn('T', '\uFFFF');
        CharRange charRange1 = CharRange.is('J');
        boolean boolean0 = charRange0.contains(charRange1);
        assertEquals('J', charRange1.getEnd());
        assertTrue(boolean0);
        assertEquals('\uFFFF', charRange0.getEnd());
        assertEquals('J', charRange1.getStart());
        assertEquals('T', charRange0.getStart());
    }
}
