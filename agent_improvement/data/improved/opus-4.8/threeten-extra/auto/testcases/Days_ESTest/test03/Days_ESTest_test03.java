package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Days_ESTest_test03 extends Days_ESTest_scaffolding {

    /**
     * A {@code Days} amount is never equal to an arbitrary, non-{@code Days} object.
     * Verifies that {@link Days#equals(Object)} returns false for a plain Object,
     * while the underlying amount stays as the weeks-to-days conversion produced it.
     */
    @Test(timeout = 4000)
    public void equalsPlainObjectReturnsFalse() throws Throwable {
        int weeks = -3380;
        int expectedDays = weeks * 7; // ofWeeks converts weeks to days (7 days per week)

        Days negativeWeeks = Days.ofWeeks(weeks);
        Object unrelatedObject = new Object();

        boolean isEqual = negativeWeeks.equals(unrelatedObject);

        assertFalse("Days must not equal a non-Days object", isEqual);
        assertEquals(expectedDays, negativeWeeks.getAmount());
    }
}
