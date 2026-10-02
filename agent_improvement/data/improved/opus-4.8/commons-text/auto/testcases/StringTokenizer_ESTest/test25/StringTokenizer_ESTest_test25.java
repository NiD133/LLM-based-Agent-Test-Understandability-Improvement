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
public class StringTokenizer_ESTest_test25 extends StringTokenizer_ESTest_scaffolding {

    /**
     * A freshly created tokenizer has not advanced past any token yet, so the
     * index of the "previous" token should be -1 before any iteration occurs.
     */
    @Test(timeout = 4000)
    public void previousIndexOnNewTokenizerIsMinusOne() throws Throwable {
        StringTokenizer tsvTokenizer = StringTokenizer.getTSVInstance();

        int previousIndex = tsvTokenizer.previousIndex();

        assertEquals(-1, previousIndex);
    }
}
