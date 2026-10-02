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
     * AmPm.from(TemporalAccessor) extracts the AMPM_OF_DAY field from the given
     * temporal. A LocalDate carries only date information and does not support
     * AMPM_OF_DAY, so the conversion must fail with a DateTimeException.
     */
    @Test(timeout = 4000)
    public void from_localDate_throwsDateTimeException() throws Throwable {
        LocalDate dateWithoutTimeOfDay = MockLocalDate.now();

        try {
            AmPm.from(dateWithoutTimeOfDay);
            fail("Expected DateTimeException: LocalDate has no AM/PM information");
        } catch (DateTimeException expected) {
            // Message: "Unable to obtain AmPm from TemporalAccessor: <date> of type java.time.LocalDate"
            verifyException("org.threeten.extra.AmPm", expected);
        }
    }
}
