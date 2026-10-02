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
public class StringTokenizer_ESTest_test18 extends StringTokenizer_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test18() throws Throwable {
        final String tokenizedText = "z!S]_Cf!Rm6c";
        final char initialDelimiter = 'x';
        final int expectedTokenCount = 0;

        final StringTokenizer tokenizer = new StringTokenizer(tokenizedText, initialDelimiter);
        tokenizer.setDelimiterString(tokenizedText);

        final int actualTokenCount = tokenizer.size();

        assertEquals(expectedTokenCount, actualTokenCount);
    }
}
