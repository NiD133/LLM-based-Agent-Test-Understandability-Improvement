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
public class CharRange_ESTest_test04 extends CharRange_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        CharRange charRange0 = CharRange.isNot('2');
        CharRange charRange1 = CharRange.isIn('2', 'U');
        boolean boolean0 = charRange1.equals(charRange0);
        assertTrue(charRange0.isNegated());
        assertEquals('2', charRange1.getStart());
        assertEquals('U', charRange1.getEnd());
        assertFalse(boolean0);
        assertEquals('2', charRange0.getStart());
        assertEquals('2', charRange0.getEnd());
    }
}
