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
public class CharRange_ESTest_test22 extends CharRange_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test22() throws Throwable {
        CharRange charRange0 = CharRange.isNotIn('T', '\uFFFF');
        Consumer<Object> consumer0 = (Consumer<Object>) mock(Consumer.class, new ViolatedAssumptionAnswer());
        charRange0.forEach(consumer0);
        assertEquals('T', charRange0.getStart());
        assertEquals('\uFFFF', charRange0.getEnd());
    }
}
