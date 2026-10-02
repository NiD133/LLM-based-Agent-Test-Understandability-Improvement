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
public class Seconds_ESTest_test02 extends Seconds_ESTest_scaffolding {

    /**
     * Verifies that {@code equals} is reflexive: a {@code Seconds} instance is
     * always equal to itself.
     */
    @Test(timeout = 4000)
    public void equalsIsReflexiveForSameInstance() throws Throwable {
        Seconds zeroSeconds = Seconds.ZERO;

        boolean isEqualToItself = zeroSeconds.equals(zeroSeconds);

        assertTrue("A Seconds instance should be equal to itself", isEqualToItself);
    }
}
