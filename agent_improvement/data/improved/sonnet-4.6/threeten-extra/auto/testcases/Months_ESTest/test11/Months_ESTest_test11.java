package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Months_ESTest_test11 extends Months_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_multipliedByOne_returnsSameAmount() throws Throwable {
        // Multiplying Months.ONE by scalar 1 should return the same amount (identity operation)
        Months oneMonth = Months.ONE;
        Months result = oneMonth.multipliedBy(1);
        assertEquals(1, result.getAmount());
    }
}
