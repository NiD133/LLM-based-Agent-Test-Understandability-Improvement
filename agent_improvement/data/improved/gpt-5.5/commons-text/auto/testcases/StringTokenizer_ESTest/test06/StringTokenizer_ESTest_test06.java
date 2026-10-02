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
public class StringTokenizer_ESTest_test06 extends StringTokenizer_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test06() throws Throwable {
        StringTokenizer tsvTokenizerWithNoInput = StringTokenizer.getTSVInstance((String) null);
        boolean emptyTokensAreReturnedAsNull = tsvTokenizerWithNoInput.isEmptyTokenAsNull();

        assertFalse(emptyTokensAreReturnedAsNull);
    }
}
