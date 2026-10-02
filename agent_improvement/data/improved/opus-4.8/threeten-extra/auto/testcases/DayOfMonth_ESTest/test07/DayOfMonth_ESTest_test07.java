package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.DateTimeException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.chrono.MockMinguoDate;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DayOfMonth_ESTest_test07 extends DayOfMonth_ESTest_scaffolding {

    /**
     * A {@link DayOfMonth} only carries a day value; it has no full date information
     * and is not tied to the ISO chronology in a way the Minguo calendar can use.
     * Asking {@code MinguoDate.from(...)} to build a date from it must therefore fail:
     * internally it tries to obtain a {@code LocalDate} from the DayOfMonth and cannot,
     * so a {@link DateTimeException} is raised from {@code java.time.LocalDate}.
     */
    @Test(timeout = 4000)
    public void from_dayOfMonth_cannotBuildMinguoDate_throwsDateTimeException() throws Throwable {
        DayOfMonth dayOfMonth = DayOfMonth.now();

        try {
            MockMinguoDate.from(dayOfMonth);
            fail("Expected DateTimeException: a DayOfMonth cannot be converted to a MinguoDate");
        } catch (DateTimeException e) {
            // "Unable to obtain LocalDate from TemporalAccessor: DayOfMonth:.. of type org.threeten.extra.DayOfMonth"
            verifyException("java.time.LocalDate", e);
        }
    }
}
