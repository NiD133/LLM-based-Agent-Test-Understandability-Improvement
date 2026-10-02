package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharRange_ESTest_test02 extends CharRange_ESTest_scaffolding {

    /**
     * A negated single-character range and a plain single-character range that
     * cover the same character are not equal, because equality also considers
     * the negated flag. Both ranges still report the same start and end char.
     */
    @Test(timeout = 4000)
    public void test02() throws Throwable {
        final char quote = '"';

        CharRange negatedQuoteRange = CharRange.isNotIn(quote, quote);
        CharRange plainQuoteRange = CharRange.is(quote);

        boolean rangesAreEqual = plainQuoteRange.equals(negatedQuoteRange);
        assertFalse(rangesAreEqual);

        assertEquals(quote, negatedQuoteRange.getStart());
        assertEquals(quote, negatedQuoteRange.getEnd());
        assertEquals(quote, plainQuoteRange.getStart());
        assertEquals(quote, plainQuoteRange.getEnd());
    }
}
