package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StrTokenizer_ESTest_test15 extends StrTokenizer_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test15() throws Throwable {
        StrTokenizer strTokenizer0 = StrTokenizer.getTSVInstance("krYC");
        strTokenizer0.nextToken();
        assertEquals(1, strTokenizer0.nextIndex());
        String string0 = strTokenizer0.previous();
        assertEquals("krYC", string0);
    }
}
