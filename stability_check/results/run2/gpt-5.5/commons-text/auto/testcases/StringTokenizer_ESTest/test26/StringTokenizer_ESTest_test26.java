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
public class StringTokenizer_ESTest_test26 extends StringTokenizer_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test26() throws Throwable {
        final char[] emptyCsvInput = new char[7];
        final StringTokenizer csvTokenizer = StringTokenizer.getCSVInstance(emptyCsvInput);

        final int nextTokenIndex = csvTokenizer.nextIndex();

        assertEquals(0, nextTokenIndex);
    }
}
