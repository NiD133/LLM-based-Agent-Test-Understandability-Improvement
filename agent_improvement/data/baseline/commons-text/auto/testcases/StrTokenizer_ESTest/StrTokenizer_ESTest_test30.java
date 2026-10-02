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
public class StrTokenizer_ESTest_test30 extends StrTokenizer_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test30() throws Throwable {
        char[] charArray0 = new char[4];
        StrTokenizer strTokenizer0 = StrTokenizer.getCSVInstance(charArray0);
        int int0 = strTokenizer0.size();
        assertEquals(1, int0);
        assertEquals(0, strTokenizer0.nextIndex());
    }
}
