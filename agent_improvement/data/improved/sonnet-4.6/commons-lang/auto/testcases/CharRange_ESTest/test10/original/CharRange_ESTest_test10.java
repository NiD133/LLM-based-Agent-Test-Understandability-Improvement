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

    @Test(timeout = 4000)
    public void test10() throws Throwable {
        CharRange charRange0 = CharRange.isNotIn('\u0000', '\u0000');
        CharRange charRange1 = CharRange.is('\u0000');
        boolean boolean0 = charRange1.contains(charRange0);
        assertEquals('\u0000', charRange1.getStart());
        assertFalse(boolean0);
        assertEquals('\u0000', charRange0.getStart());
        assertEquals('\u0000', charRange1.getEnd());
        assertEquals('\u0000', charRange0.getEnd());
    }
}
