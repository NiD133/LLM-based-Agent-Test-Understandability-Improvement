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
     * Half.from(TemporalAccessor) must reject a temporal that carries no
     * half-of-year information. A DayOfWeek has no month/half data, so the
     * conversion cannot succeed and a DateTimeException is expected.
     */
    @Test(timeout = 4000)
    public void fromDayOfWeekThrowsDateTimeException() throws Throwable {
        DayOfWeek dayWithoutHalfInfo = DayOfWeek.WEDNESDAY;

        try {
            Half.from(dayWithoutHalfInfo);
            fail("Expected DateTimeException: a DayOfWeek cannot be converted to a Half");
        } catch (DateTimeException expected) {
            // Message: "Unable to obtain Half from TemporalAccessor: WEDNESDAY of type java.time.DayOfWeek"
            verifyException("org.threeten.extra.Half", expected);
        }
    }
}
