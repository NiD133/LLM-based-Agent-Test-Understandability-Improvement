package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockLocalDateTime;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Quarter_ESTest_test01 extends Quarter_ESTest_scaffolding {

    // adjustInto(Q4) shifts the datetime to the fourth quarter, so the result differs from the original
    @Test(timeout = 4000)
    public void test_adjustIntoQ4_producesDateTimeDifferentFromOriginal() throws Throwable {
        LocalDateTime originalDateTime = MockLocalDateTime.now();
        Temporal adjustedDateTime = Quarter.Q4.adjustInto(originalDateTime);
        assertFalse(adjustedDateTime.equals((Object) originalDateTime));
    }
}
