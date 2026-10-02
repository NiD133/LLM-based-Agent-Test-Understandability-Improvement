package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Months_ESTest_test11 extends Months_ESTest_scaffolding {

    /**
     * Multiplying {@code Months.ONE} by the scalar 1 must leave the amount unchanged at 1 month.
     */
    @Test(timeout = 4000)
    public void multiplyingOneMonthByOneKeepsAmountUnchanged() throws Throwable {
        Months oneMonth = Months.ONE;

        Months result = oneMonth.multipliedBy(1);

        assertEquals(1, result.getAmount());
    }
}
