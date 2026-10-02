package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.DateTimeException;
import java.time.LocalDate;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockLocalDate;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AmPm_ESTest_test09 extends AmPm_ESTest_scaffolding {

    /**
     * Verifies that AmPm.from() throws DateTimeException when given a LocalDate,
     * because LocalDate has no time-of-day information and therefore no AM/PM field.
     */
    @Test(timeout = 4000)
    public void test_fromLocalDate_throwsDateTimeException() throws Throwable {
        LocalDate dateWithoutTimeInfo = MockLocalDate.now();

        try {
            AmPm.from(dateWithoutTimeInfo);
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException e) {
            verifyException("org.threeten.extra.AmPm", e);
        }
    }
}
