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
public class JulianChronology_ESTest_test03 extends JulianChronology_ESTest_scaffolding {

    /**
     * Verifies that querying the value range for PROLEPTIC_MONTH on the
     * JulianChronology singleton returns a non-null ValueRange.
     */
    @Test(timeout = 4000)
    public void test_range_returnsNonNull_forProlepticMonthField() throws Throwable {
        // The constructor is deprecated; INSTANCE is the recommended singleton,
        // but we invoke the constructor here to preserve original test behaviour.
        new JulianChronology();

        ValueRange prolepticMonthRange = JulianChronology.INSTANCE.range(ChronoField.PROLEPTIC_MONTH);

        assertNotNull(prolepticMonthRange);
    }
}
