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
        char[] inputWithNullCharacter = new char[1];
        StringMatcher delimiterMatcher = mock(StringMatcher.class, new ViolatedAssumptionAnswer());

        doReturn((String) null, (String) null).when(delimiterMatcher).toString();
        doReturn(0, 0).when(delimiterMatcher).isMatch(any(char[].class), anyInt(), anyInt(), anyInt());

        StringTokenizer tokenizer = new StringTokenizer(inputWithNullCharacter, delimiterMatcher);
        tokenizer.previousToken();

        String tokenizerDescription = tokenizer.toString();
        assertEquals("StringTokenizer[\u0000]", tokenizerDescription);
        assertTrue(tokenizer.isIgnoreEmptyTokens());
    }
}
