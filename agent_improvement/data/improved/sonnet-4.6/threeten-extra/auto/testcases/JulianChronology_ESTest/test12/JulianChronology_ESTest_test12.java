package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.DateTimeException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JulianChronology_ESTest_test12 extends JulianChronology_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_date_withInvalidMonth_throwsDateTimeException() throws Throwable {
        JulianChronology chronology = JulianChronology.INSTANCE;

        // Month 63 is out of the valid range (1-12), so creating a date should fail
        try {
            chronology.date(63, 63, 63);
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException e) {
            verifyException("java.time.temporal.ValueRange", e);
        }
    }
}
