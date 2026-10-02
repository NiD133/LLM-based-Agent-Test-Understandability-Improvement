package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Years_ESTest_test02 extends Years_ESTest_scaffolding {

    /**
     * Verifies that comparing a Years instance to {@code null} returns false,
     * and that the comparison does not alter the instance's amount.
     */
    @Test(timeout = 4000)
    public void equals_nullArgument_returnsFalse() throws Throwable {
        int yearsAmount = 2146862987;
        Years years = Years.of(yearsAmount);

        boolean isEqualToNull = years.equals((Object) null);

        assertFalse("Years should never be equal to null", isEqualToNull);
        assertEquals("Amount should remain unchanged after the comparison",
                yearsAmount, years.getAmount());
    }
}
