package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import org.apache.commons.text.matcher.StringMatcher;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringTokenizer_ESTest_test11 extends StringTokenizer_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test11() throws Throwable {
        char[] charArray0 = new char[10];
        StringTokenizer stringTokenizer0 = new StringTokenizer(charArray0, '(', '\u0000');
        String string0 = stringTokenizer0.previousToken();
        assertNull(string0);
    }
}
