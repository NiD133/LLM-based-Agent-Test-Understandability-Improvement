package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.DateTimeException;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.Period;
import java.time.YearMonth;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import java.time.temporal.UnsupportedTemporalTypeException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockOffsetDateTime;
import org.evosuite.runtime.mock.java.time.MockYearMonth;
import org.evosuite.runtime.mock.java.time.MockZonedDateTime;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Minutes_ESTest_test01 extends Minutes_ESTest_scaffolding {

    /**
     * Verifies that Minutes created from a negative hour count stores the correct
     * total minutes (-899 hours * 60 = -53940 minutes), and that equals() returns
     * false when compared to a non-Minutes object.
     */
    @Test(timeout = 4000)
    public void test_ofHours_negativeValue_equalsReturnsFalseForNonMinutesObject() throws Throwable {
        // -899 hours converts to -899 * 60 = -53940 minutes
        Minutes negativeMinutes = Minutes.ofHours(-899);
        Object nonMinutesObject = new Object();

        boolean isEqual = negativeMinutes.equals(nonMinutesObject);

        assertEquals(-53940, negativeMinutes.getAmount());
        assertFalse(isEqual);
    }
}
