package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Months_ESTest_test01 extends Months_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_equalsReturnsFalse_whenComparedToNonMonthsObject() throws Throwable {
        Months negativeMonths = Months.of(-1428);
        Object nonMonthsObject = new Object();

        boolean isEqual = negativeMonths.equals(nonMonthsObject);

        assertEquals(-1428, negativeMonths.getAmount());
        assertFalse(isEqual);
    }
}
