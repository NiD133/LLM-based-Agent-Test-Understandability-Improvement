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
public class Hours_ESTest_test22 extends Hours_ESTest_scaffolding {

    private static final String TEXT_WITH_NEGATIVE_HOURS = "PT-1485H";
    private static final String HOURS_CLASS_NAME = "org.threeten.extra.Hours";

    @Test(timeout = 4000)
    public void test22() throws Throwable {
        try {
            Hours.parse(TEXT_WITH_NEGATIVE_HOURS);
            // Original EvoSuite assertion was disabled as unstable; preserving that behavior.
        } catch (DateTimeParseException exception) {
            verifyException(HOURS_CLASS_NAME, exception);
        }
    }
}
