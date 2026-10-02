package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.DateTimeException;
import java.time.Duration;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.Period;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.util.List;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.evosuite.runtime.mock.java.time.MockOffsetDateTime;
import org.evosuite.runtime.mock.java.time.MockZonedDateTime;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Hours_ESTest_test01 extends Hours_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_hoursFromZeroDurationAndHoursOfTwo_areNotEqual() throws Throwable {
        // Create zero hours from Duration.ZERO and two hours directly
        Hours zeroHours = Hours.from(Duration.ZERO);
        Hours twoHours = Hours.of(2);

        // Verify each instance holds the expected amount
        assertEquals(0, zeroHours.getAmount());
        assertEquals(2, twoHours.getAmount());

        // Verify that instances with different amounts are not equal to each other
        assertFalse(twoHours.equals(zeroHours));
        assertFalse(zeroHours.equals((Object) twoHours));
    }
}
