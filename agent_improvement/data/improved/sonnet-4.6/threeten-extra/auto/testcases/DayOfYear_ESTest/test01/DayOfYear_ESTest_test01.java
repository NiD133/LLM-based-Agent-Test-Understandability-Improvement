package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.DateTimeException;
import java.time.ZoneOffset;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DayOfYear_ESTest_test01 extends DayOfYear_ESTest_scaffolding {

    /**
     * Verifies that DayOfYear.from() throws DateTimeException when given a
     * ZoneOffset, which carries no DAY_OF_YEAR field and cannot be converted
     * to a LocalDate.
     */
    @Test(timeout = 4000)
    public void test_fromZoneOffset_throwsDateTimeException() throws Throwable {
        ZoneOffset zoneOffset0 = ZoneOffset.MAX;
        try {
            DayOfYear.from(zoneOffset0);
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException e) {
            //
            // Unable to obtain DayOfYear from TemporalAccessor: +18:00 of type java.time.ZoneOffset
            //
            verifyException("org.threeten.extra.DayOfYear", e);
        }
    }
}
