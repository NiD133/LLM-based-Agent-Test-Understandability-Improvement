package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.DateTimeException;
import java.time.DayOfWeek;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Half_ESTest_test00 extends Half_ESTest_scaffolding {

    /**
     * A DayOfWeek carries no half-of-year information, so Half.from(...)
     * cannot convert it and must reject it with a DateTimeException.
     */
    @Test(timeout = 4000)
    public void fromRejectsDayOfWeekWithDateTimeException() throws Throwable {
        DayOfWeek unconvertibleTemporal = DayOfWeek.WEDNESDAY;

        try {
            Half.from(unconvertibleTemporal);
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException e) {
            // Message: "Unable to obtain Half from TemporalAccessor: WEDNESDAY of type java.time.DayOfWeek"
            verifyException("org.threeten.extra.Half", e);
        }
    }
}
