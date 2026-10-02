package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.Clock;
import java.time.DateTimeException;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.chrono.ChronoZonedDateTime;
import java.time.chrono.Era;
import java.time.chrono.IsoEra;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.ValueRange;
import java.util.List;
import java.util.Locale;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockClock;
import org.evosuite.runtime.mock.java.time.MockOffsetDateTime;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Symmetry010Chronology_ESTest_test00 extends Symmetry010Chronology_ESTest_scaffolding {

    private static final int YEAR_OF_ERA = 4;
    private static final int INVALID_DAY_OF_YEAR_BEFORE_FIRST_DAY = 0;

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        Symmetry010Chronology chronology = Symmetry010Chronology.INSTANCE;
        Symmetry454Date currentSymmetry454Date = Symmetry454Date.now();
        IsoEra currentEra = currentSymmetry454Date.getEra();

        try {
            chronology.dateYearDay((Era) currentEra, YEAR_OF_ERA, INVALID_DAY_OF_YEAR_BEFORE_FIRST_DAY);
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException e) {
            verifyException("java.time.temporal.ValueRange", e);
        }
    }
}
