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
public class StringTokenizer_ESTest_test17 extends StringTokenizer_ESTest_scaffolding {

    private static final String INPUT_WITHOUT_TABS = ",OF0)2fR[p0$";
    private static final int INITIAL_PREVIOUS_INDEX = -1;

    @Test(timeout = 4000)
    public void test17() throws Throwable {
        StringTokenizer tokenizer = StringTokenizer.getTSVInstance(INPUT_WITHOUT_TABS);

        assertEquals(INITIAL_PREVIOUS_INDEX, tokenizer.previousIndex());

        tokenizer.next();
        String previousToken = tokenizer.previous();

        assertEquals(INPUT_WITHOUT_TABS, previousToken);
    }
}
