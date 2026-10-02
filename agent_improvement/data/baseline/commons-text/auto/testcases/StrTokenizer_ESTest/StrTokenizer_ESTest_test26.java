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
public class StrTokenizer_ESTest_test26 extends StrTokenizer_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test26() throws Throwable {
        StrTokenizer strTokenizer0 = StrTokenizer.getTSVInstance("krYC");
        // Undeclared exception!
        try {
            strTokenizer0.add((String) null);
            fail("Expecting exception: UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            //
            // add() is unsupported
            //
            verifyException("org.apache.commons.text.StrTokenizer", e);
        }
    }
}
