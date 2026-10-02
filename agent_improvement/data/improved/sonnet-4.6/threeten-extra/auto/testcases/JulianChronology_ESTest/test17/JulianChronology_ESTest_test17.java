package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.Clock;
import java.time.DateTimeException;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoZonedDateTime;
import java.time.chrono.Era;
import java.time.chrono.ThaiBuddhistEra;
import java.time.format.ResolverStyle;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalField;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.time.temporal.ValueRange;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockZonedDateTime;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JulianChronology_ESTest_test17 extends JulianChronology_ESTest_scaffolding {

    /**
     * Verifies that the current Julian date (obtained via the singleton INSTANCE)
     * falls in the AD era, confirming that dateNow() returns a valid present-day date.
     */
    @Test(timeout = 4000)
    public void test17() throws Throwable {
        // Instantiate via the deprecated constructor (exercises the constructor code path)
        JulianChronology chronology = new JulianChronology();

        // Access the singleton INSTANCE through the local reference and get today's Julian date
        JulianDate today = chronology.INSTANCE.dateNow();

        // The current date must be in the Anno Domini (AD) era
        assertEquals(JulianEra.AD, today.getEra());
    }
}
