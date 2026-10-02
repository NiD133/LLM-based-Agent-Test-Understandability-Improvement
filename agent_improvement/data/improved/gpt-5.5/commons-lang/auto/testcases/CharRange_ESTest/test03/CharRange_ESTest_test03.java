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
public class CharRange_ESTest_test03 extends CharRange_ESTest_scaffolding {

    private static final char DEL_CHARACTER = '\u007F';
    private static final char NEGATED_RANGE_LOWER_ENDPOINT = 'Y';
    private static final char NORMAL_RANGE_LOWER_ENDPOINT = 'V';

    @Test(timeout = 4000)
    public void test03() throws Throwable {
        CharRange negatedRange = CharRange.isNotIn(DEL_CHARACTER, NEGATED_RANGE_LOWER_ENDPOINT);
        CharRange normalRange = CharRange.isIn(DEL_CHARACTER, NORMAL_RANGE_LOWER_ENDPOINT);

        boolean rangesAreEqual = normalRange.equals(negatedRange);

        assertFalse(rangesAreEqual);
        assertEquals(DEL_CHARACTER, normalRange.getEnd());
        assertEquals(NORMAL_RANGE_LOWER_ENDPOINT, normalRange.getStart());
        assertEquals(DEL_CHARACTER, negatedRange.getEnd());
        assertEquals(NEGATED_RANGE_LOWER_ENDPOINT, negatedRange.getStart());
    }
}
