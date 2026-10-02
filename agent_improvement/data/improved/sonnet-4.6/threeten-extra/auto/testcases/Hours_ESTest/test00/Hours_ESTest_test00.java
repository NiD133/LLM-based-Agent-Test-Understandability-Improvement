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
        // Arrange: create a negative Hours value and two independent negations of it
        Hours negativeHours = Hours.of(-4696);
        Hours firstNegation = negativeHours.negated();
        Hours secondNegation = negativeHours.negated();

        // Two independent calls to negated() on the same value should produce equal results
        assertTrue(firstNegation.equals(secondNegation));

        // The original negative value must not equal its positive negation
        assertFalse(negativeHours.equals((Object) firstNegation));

        // The negated amount should be the absolute (positive) value
        assertEquals(4696, secondNegation.getAmount());

        // Equality check is symmetric: negation should also not equal the original
        assertFalse(secondNegation.equals((Object) negativeHours));

        // The original Hours instance must retain its negative amount unchanged
        assertEquals((-4696), negativeHours.getAmount());
    }
}
