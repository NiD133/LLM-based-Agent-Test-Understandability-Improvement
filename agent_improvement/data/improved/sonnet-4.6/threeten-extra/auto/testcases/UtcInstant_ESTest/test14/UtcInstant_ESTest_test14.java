package org.threeten.extra.scale;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.StringWriter;
import java.time.Duration;
import java.time.Instant;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class UtcInstant_ESTest_test14 extends UtcInstant_ESTest_scaffolding {

    // A far-future date at 23:59:59.999999997 UTC — 3 nanoseconds before midnight
    private static final String FAR_FUTURE_TIMESTAMP = "+2739877-01-02T23:59:59.999999997Z";

    // Expected nano-of-day: 23h 59m 59s + 999,999,997 ns = 86,399,999,999,997 ns
    private static final long EXPECTED_NANO_OF_DAY = 86399999999997L;

    // Expected Modified Julian Day for +2739877-01-02 (days since 1858-11-17)
    private static final long EXPECTED_MODIFIED_JULIAN_DAY = 1000040586L;

    @Test(timeout = 4000)
    public void test_parse_farFutureDateNearMidnight_returnsCorrectNanoOfDayAndMJD() throws Throwable {
        UtcInstant utcInstant = UtcInstant.parse(FAR_FUTURE_TIMESTAMP);

        assertEquals(EXPECTED_NANO_OF_DAY, utcInstant.getNanoOfDay());
        assertEquals(EXPECTED_MODIFIED_JULIAN_DAY, utcInstant.getModifiedJulianDay());
    }
}
