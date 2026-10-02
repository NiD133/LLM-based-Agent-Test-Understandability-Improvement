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
public class StringTokenizer_ESTest_test31 extends StringTokenizer_ESTest_scaffolding {

    private static final String INPUT_TEXT = "1EDH\u007F+{PcZzyE";

    @Test(timeout = 4000)
    public void test31() throws Throwable {
        StringMatcher delimiterMatcher = null;
        StringMatcher quoteMatcher = null;

        StringTokenizer tokenizer = new StringTokenizer(INPUT_TEXT, delimiterMatcher, quoteMatcher);

        assertTrue(tokenizer.isIgnoreEmptyTokens());
    }
}
