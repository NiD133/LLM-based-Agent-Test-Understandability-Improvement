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
public class StrTokenizer_ESTest_test08 extends StrTokenizer_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test08() throws Throwable {
        StrTokenizer strTokenizer0 = new StrTokenizer();
        StrTokenizer strTokenizer1 = strTokenizer0.setQuoteChar('');
        char[] charArray0 = new char[9];
        charArray0[0] = '';
        strTokenizer0.reset(charArray0);
        strTokenizer1.setIgnoredChar('');
        strTokenizer1.nextToken();
        assertEquals(1, strTokenizer1.nextIndex());
    }
}
