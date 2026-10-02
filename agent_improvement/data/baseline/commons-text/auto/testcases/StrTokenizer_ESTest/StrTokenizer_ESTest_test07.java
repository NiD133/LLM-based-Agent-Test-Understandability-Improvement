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
public class StrTokenizer_ESTest_test07 extends StrTokenizer_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test07() throws Throwable {
        char[] charArray0 = new char[6];
        charArray0[5] = '.';
        StrTokenizer strTokenizer0 = new StrTokenizer(charArray0, '.');
        Consumer<Object> consumer0 = (Consumer<Object>) mock(Consumer.class, new ViolatedAssumptionAnswer());
        strTokenizer0.forEachRemaining(consumer0);
        assertEquals(1, strTokenizer0.nextIndex());
    }
}
