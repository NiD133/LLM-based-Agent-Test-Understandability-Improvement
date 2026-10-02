package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.DateTimeException;
import java.time.Duration;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.chrono.MinguoDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.time.temporal.UnsupportedTemporalTypeException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.evosuite.runtime.mock.java.time.MockZonedDateTime;
import org.evosuite.runtime.mock.java.time.chrono.MockMinguoDate;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Days_ESTest_test00 extends Days_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        // Two Days instances created from the same number of weeks should be equal,
        // and getAmount() should return the equivalent day count (-3574 weeks * 7 = -25018 days).
        Days firstInstance  = Days.ofWeeks(-3574);
        Days secondInstance = Days.ofWeeks(-3574);

        boolean areEqual = firstInstance.equals(secondInstance);
        assertTrue(areEqual);
        assertEquals(-25018, secondInstance.getAmount());
    }
}
