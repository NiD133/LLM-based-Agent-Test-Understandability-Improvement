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
public class Minutes_ESTest_test00 extends Minutes_ESTest_scaffolding {

    // 327 hours * 60 minutes/hour = 19620 minutes
    private static final int HOURS = 327;
    private static final int EXPECTED_MINUTES = 19620;

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        // Create a Minutes instance representing 327 hours (= 19620 minutes)
        Minutes original = Minutes.ofHours(HOURS);

        // Subtract 327 minutes from 19620, yielding 19293 minutes
        Minutes afterSubtraction = original.minus(HOURS);

        // Add those 327 minutes back, restoring 19620 minutes
        Minutes restored = afterSubtraction.plus(HOURS);

        // restored and original both hold 19620 minutes, so they must be equal
        boolean restoredEqualsOriginal = restored.equals(original);
        assertTrue(restoredEqualsOriginal);
        assertEquals(EXPECTED_MINUTES, restored.getAmount());

        // restored (19620) and afterSubtraction (19293) differ, so not equal
        assertFalse(restored.equals((Object) afterSubtraction));

        // afterSubtraction (19293) and original (19620) also differ
        assertFalse(afterSubtraction.equals((Object) original));
    }
}
