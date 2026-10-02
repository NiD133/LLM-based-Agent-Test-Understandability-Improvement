package org.threeten.extra;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Months_ESTest_test26 extends Months_ESTest_scaffolding {

    /**
     * The hash code of a {@code Months} value is defined as its month count,
     * so {@link Months#ONE} (one month) must hash to 1.
     */
    @Test(timeout = 4000)
    public void hashCodeOfOneMonthEqualsItsAmount() throws Throwable {
        Months oneMonth = Months.ONE;

        int hash = oneMonth.hashCode();

        assertEquals(1, hash);
    }
}
