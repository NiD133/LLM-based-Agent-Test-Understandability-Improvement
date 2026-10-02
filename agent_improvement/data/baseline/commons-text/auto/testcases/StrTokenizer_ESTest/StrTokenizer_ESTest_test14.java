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
public class StrTokenizer_ESTest_test14 extends StrTokenizer_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test14() throws Throwable {
        char[] charArray0 = new char[4];
        StrTokenizer strTokenizer0 = new StrTokenizer(charArray0, '5', '\u0000');
        boolean boolean0 = strTokenizer0.hasNext();
        assertTrue(boolean0);
    }
}
