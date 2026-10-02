package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Hours_ESTest_test17 extends Hours_ESTest_scaffolding {

    /**
     * A negative amount of hours is not zero: isZero() returns false while
     * getAmount() preserves the original negative value.
     */
    @Test(timeout = 4000)
    public void isZeroReturnsFalseForNegativeHours() throws Throwable {
        Hours negativeHours = Hours.of(-1967);

        boolean zero = negativeHours.isZero();

        assertFalse("a non-zero amount of hours should not be reported as zero", zero);
        assertEquals("getAmount() should return the value passed to Hours.of",
                -1967, negativeHours.getAmount());
    }
}
