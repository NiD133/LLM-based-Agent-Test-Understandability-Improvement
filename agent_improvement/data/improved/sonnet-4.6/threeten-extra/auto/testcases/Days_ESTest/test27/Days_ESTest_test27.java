package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Days_ESTest_test27 extends Days_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test27_hashCodeDoesNotThrowAndGetAmountReturnsCorrectValue() throws Throwable {
        Days threeDays = Days.of(3);

        // hashCode() is based on the day count — calling it must not throw
        threeDays.hashCode();

        assertEquals(3, threeDays.getAmount());
    }
}
