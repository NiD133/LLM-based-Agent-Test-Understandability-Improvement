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
public class StringTokenizer_ESTest_test01 extends StringTokenizer_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        char[] charArray0 = new char[1];
        StringMatcher stringMatcher0 = mock(StringMatcher.class, new ViolatedAssumptionAnswer());
        doReturn((String) null, (String) null).when(stringMatcher0).toString();
        doReturn(0, 0).when(stringMatcher0).isMatch(any(char[].class), anyInt(), anyInt(), anyInt());
        StringTokenizer stringTokenizer0 = new StringTokenizer(charArray0, stringMatcher0);
        stringTokenizer0.previousToken();
        String string0 = stringTokenizer0.toString();
        assertEquals("StringTokenizer[\u0000]", string0);
        assertTrue(stringTokenizer0.isIgnoreEmptyTokens());
    }
}
