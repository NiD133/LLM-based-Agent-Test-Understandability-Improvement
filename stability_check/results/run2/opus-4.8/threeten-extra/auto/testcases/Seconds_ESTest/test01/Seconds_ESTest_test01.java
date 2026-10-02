package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.CharBuffer;
import java.time.DateTimeException;
import java.time.Duration;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import java.time.temporal.UnsupportedTemporalTypeException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.evosuite.runtime.mock.java.time.MockZonedDateTime;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Seconds_ESTest_test01 extends Seconds_ESTest_scaffolding {

    /**
     * Subtracting a {@link Duration} from a {@link Seconds} amount returns a new,
     * distinct instance with the reduced number of seconds, while the original
     * instance is left unchanged (Seconds is immutable).
     */
    @Test(timeout = 4000)
    public void subtractDurationReturnsReducedImmutableCopy() throws Throwable {
        // 396 hours expressed as seconds: 396 * 3600 = 1425600
        Seconds threeHundredNinetySixHours = Seconds.ofHours(396);
        Duration threeHundredNinetySixSeconds = Duration.ofSeconds(396L);

        // Subtract 396 seconds: 1425600 - 396 = 1425204
        Seconds reduced = threeHundredNinetySixHours.minus((TemporalAmount) threeHundredNinetySixSeconds);

        assertEquals("subtraction should reduce the amount by 396 seconds",
                1425204, reduced.getAmount());
        assertEquals("the original amount must be unchanged after minus()",
                1425600, threeHundredNinetySixHours.getAmount());

        // The two amounts differ, so they must not be equal in either direction.
        assertFalse("original should not equal the reduced copy",
                threeHundredNinetySixHours.equals(reduced));
        assertFalse("reduced copy should not equal the original",
                reduced.equals((Object) threeHundredNinetySixHours));
    }
}
