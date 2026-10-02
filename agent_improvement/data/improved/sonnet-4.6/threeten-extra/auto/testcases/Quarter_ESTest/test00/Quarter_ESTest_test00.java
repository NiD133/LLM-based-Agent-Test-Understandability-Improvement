package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.DateTimeException;
import java.time.chrono.HijrahDate;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.chrono.MockHijrahDate;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Quarter_ESTest_test00 extends Quarter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void adjustInto_nonIsoHijrahDate_throwsDateTimeException() throws Throwable {
        Quarter q1 = Quarter.Q1;
        HijrahDate hijrahDate = MockHijrahDate.now();
        try {
            q1.adjustInto(hijrahDate);
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException e) {
            verifyException("org.threeten.extra.Quarter", e);
        }
    }
}
