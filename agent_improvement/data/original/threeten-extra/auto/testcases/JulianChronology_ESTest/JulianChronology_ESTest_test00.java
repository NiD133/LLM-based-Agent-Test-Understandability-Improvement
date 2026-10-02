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
public class JulianChronology_ESTest_test00 extends JulianChronology_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        JulianChronology julianChronology0 = JulianChronology.INSTANCE;
        ZoneOffset zoneOffset0 = ZoneOffset.MIN;
        JulianDate julianDate0 = julianChronology0.dateNow((ZoneId) zoneOffset0);
        // Undeclared exception!
        try {
            julianDate0.with((TemporalAdjuster) zoneOffset0);
            fail("Expecting exception: UnsupportedTemporalTypeException");
        } catch (UnsupportedTemporalTypeException e) {
            //
            // Unsupported field: OffsetSeconds
            //
            verifyException("org.threeten.extra.chrono.AbstractDate", e);
        }
    }
}
