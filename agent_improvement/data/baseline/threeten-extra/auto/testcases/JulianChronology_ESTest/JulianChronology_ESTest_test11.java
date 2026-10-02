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
public class JulianChronology_ESTest_test11 extends JulianChronology_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test11() throws Throwable {
        JulianChronology julianChronology0 = JulianChronology.INSTANCE;
        ThaiBuddhistEra thaiBuddhistEra0 = ThaiBuddhistEra.BEFORE_BE;
        // Undeclared exception!
        try {
            julianChronology0.dateYearDay((Era) thaiBuddhistEra0, 3, 3);
            fail("Expecting exception: ClassCastException");
        } catch (ClassCastException e) {
            //
            // Era must be JulianEra
            //
            verifyException("org.threeten.extra.chrono.JulianChronology", e);
        }
    }
}
