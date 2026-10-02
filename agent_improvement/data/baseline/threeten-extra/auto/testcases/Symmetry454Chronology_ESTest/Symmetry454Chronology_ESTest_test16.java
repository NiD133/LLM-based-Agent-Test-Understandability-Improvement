package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.Clock;
import java.time.DateTimeException;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.chrono.ChronoZonedDateTime;
import java.time.chrono.Era;
import java.time.chrono.IsoEra;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.ValueRange;
import java.util.List;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockClock;
import org.evosuite.runtime.mock.java.time.MockOffsetDateTime;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Symmetry454Chronology_ESTest_test16 extends Symmetry454Chronology_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test16() throws Throwable {
        Symmetry454Chronology symmetry454Chronology0 = new Symmetry454Chronology();
        // Undeclared exception!
        try {
            symmetry454Chronology0.date((Era) null, (-2134851390), (-2134851390), (-2134851390));
            fail("Expecting exception: ClassCastException");
        } catch (ClassCastException e) {
            //
            // Invalid era: null
            //
            verifyException("org.threeten.extra.chrono.Symmetry454Chronology", e);
        }
    }
}
