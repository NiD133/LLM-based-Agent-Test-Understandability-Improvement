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
public class StringTokenizer_ESTest_test09 extends StringTokenizer_ESTest_scaffolding {

    private static final String SINGLE_CSV_TOKEN = "%*dR|";

    @Test(timeout = 4000)
    public void test09() throws Throwable {
        StringTokenizer tokenizer = StringTokenizer.getCSVInstance(SINGLE_CSV_TOKEN);

        tokenizer.nextToken();
        assertEquals(1, tokenizer.nextIndex());

        String previousToken = tokenizer.previousToken();
        assertEquals(SINGLE_CSV_TOKEN, previousToken);
    }
}
