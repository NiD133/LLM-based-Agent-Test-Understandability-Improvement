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
public class Minutes_ESTest_test11 extends Minutes_ESTest_scaffolding {

    /**
     * Verifies that subtracting zero minutes from the ZERO constant returns the
     * very same instance. Both {@code Minutes.of(0)} and {@code Minutes.ZERO}
     * resolve to the shared ZERO singleton, and {@code minus(0)} is a no-op that
     * returns {@code this}, so the result must be identical (==) to the ZERO instance.
     */
    @Test(timeout = 4000)
    public void subtractZeroFromZeroReturnsSameInstance() throws Throwable {
        Minutes zero = Minutes.of(0);

        Minutes result = Minutes.ZERO.minus(0);

        assertSame(zero, result);
    }
}
