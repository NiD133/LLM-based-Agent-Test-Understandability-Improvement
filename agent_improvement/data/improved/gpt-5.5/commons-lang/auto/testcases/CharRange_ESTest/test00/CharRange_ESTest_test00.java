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
public class CharRange_ESTest_test00 extends CharRange_ESTest_scaffolding {

    private static final char RANGE_START = 'G';
    private static final char RANGE_END = 'j';
    private static final String EXPECTED_NEGATED_RANGE_TEXT = "^G-j";

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        CharRange negatedRange = CharRange.isNotIn(RANGE_START, RANGE_END);

        negatedRange.toString();
        String rangeText = negatedRange.toString();

        assertNotNull(rangeText);
        assertEquals(EXPECTED_NEGATED_RANGE_TEXT, rangeText);
    }
}
