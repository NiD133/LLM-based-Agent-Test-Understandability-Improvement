package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Days_ESTest_test10 extends Days_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test10_dividingOneDayByOneRetainsAmount() throws Throwable {
        Days oneDayDividedByOne = Days.ONE.dividedBy(1);
        assertEquals(1, oneDayDividedByOne.getAmount());
    }
}
