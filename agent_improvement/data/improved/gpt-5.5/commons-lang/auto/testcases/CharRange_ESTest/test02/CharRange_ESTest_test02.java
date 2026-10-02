package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import java.util.function.Consumer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharRange_ESTest_test02 extends CharRange_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test02() throws Throwable {
        final char doubleQuote = '\"';
        final CharRange notDoubleQuoteRange = CharRange.isNotIn(doubleQuote, doubleQuote);
        final CharRange doubleQuoteRange = CharRange.is(doubleQuote);

        final boolean rangesAreEqual = doubleQuoteRange.equals(notDoubleQuoteRange);

        assertEquals(doubleQuote, notDoubleQuoteRange.getStart());
        assertFalse(rangesAreEqual);
        assertEquals(doubleQuote, doubleQuoteRange.getStart());
        assertEquals(doubleQuote, notDoubleQuoteRange.getEnd());
        assertEquals(doubleQuote, doubleQuoteRange.getEnd());
    }
}
