package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Months_ESTest_test00 extends Months_ESTest_scaffolding {

    /**
     * Subtracting a negative number of months adds its magnitude, producing a
     * new {@code Months} instance that is not equal to the original.
     */
    @Test(timeout = 4000)
    public void minusNegativeAmount_addsMagnitudeAndIsNotEqualToOriginal() throws Throwable {
        Months original = Months.of(-1428);

        // minus(-2443) is equivalent to adding 2443: -1428 - (-2443) = 1015
        Months result = original.minus(-2443);

        assertEquals(1015, result.getAmount());
        assertFalse("result should differ from the original amount", result.equals(original));
        assertFalse("equality must be symmetric", original.equals(result));
    }
}
