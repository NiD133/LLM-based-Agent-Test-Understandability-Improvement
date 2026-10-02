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
public class Hours_ESTest_test00 extends Hours_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        Hours hours0 = Hours.of((-4696));
        Hours hours1 = hours0.negated();
        Hours hours2 = hours0.negated();
        boolean boolean0 = hours1.equals(hours2);
        assertTrue(boolean0);
        assertFalse(hours0.equals((Object) hours1));
        assertEquals(4696, hours2.getAmount());
        assertFalse(hours2.equals((Object) hours0));
        assertEquals((-4696), hours0.getAmount());
    }
}
