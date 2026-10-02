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
public class StringTokenizer_ESTest_test03 extends StringTokenizer_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test03() throws Throwable {
        StringTokenizer tokenizer = StringTokenizer.getTSVInstance("add() Ss unsup!oted");
        StringMatcher currentTrimmerMatcher = tokenizer.getTrimmerMatcher();

        // The setter is fluent and returns the tokenizer instance it updated.
        StringTokenizer returnedTokenizer = tokenizer.setTrimmerMatcher(currentTrimmerMatcher);

        assertSame(tokenizer, returnedTokenizer);
    }
}
