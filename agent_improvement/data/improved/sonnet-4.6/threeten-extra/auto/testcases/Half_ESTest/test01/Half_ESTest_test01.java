package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.DateTimeException;
import java.time.chrono.MinguoDate;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.chrono.MockMinguoDate;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Half_ESTest_test01 extends Half_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        Half secondHalf = Half.H2;
        // MinguoDate uses the Republic of China (Minguo) calendar, which is non-ISO;
        // Half.adjustInto must reject any non-ISO temporal with a DateTimeException.
        MinguoDate nonIsoDate = MockMinguoDate.now();
        try {
            secondHalf.adjustInto(nonIsoDate);
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException e) {
            //
            // Adjustment only supported on ISO date-time
            //
            verifyException("org.threeten.extra.Half", e);
        }
    }
}
