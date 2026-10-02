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
public class CharRange_ESTest_test00 extends CharRange_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        CharRange charRange0 = CharRange.isNotIn('G', 'j');
        charRange0.toString();
        String string0 = charRange0.toString();
        assertNotNull(string0);
        assertEquals("^G-j", string0);
    }
}
