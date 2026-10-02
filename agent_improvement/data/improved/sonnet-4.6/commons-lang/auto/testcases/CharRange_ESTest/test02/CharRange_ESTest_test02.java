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
        // Create a negated range that matches everything except the double-quote character
        CharRange negatedDoubleQuoteRange = CharRange.isNotIn('"', '"');
        // Create a non-negated range that matches only the double-quote character
        CharRange exactDoubleQuoteRange = CharRange.is('"');

        // A negated range and a non-negated range are not equal, even when they share the same character bounds
        boolean areEqual = exactDoubleQuoteRange.equals(negatedDoubleQuoteRange);
        assertFalse(areEqual);

        // Both ranges span the single double-quote character as their start and end
        assertEquals('"', negatedDoubleQuoteRange.getStart());
        assertEquals('"', negatedDoubleQuoteRange.getEnd());
        assertEquals('"', exactDoubleQuoteRange.getStart());
        assertEquals('"', exactDoubleQuoteRange.getEnd());
    }
}
