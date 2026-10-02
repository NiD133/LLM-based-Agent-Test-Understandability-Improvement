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
public class Symmetry454Chronology_ESTest_test17 extends Symmetry454Chronology_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test17() throws Throwable {
        ZoneOffset zoneOffset0 = ZoneOffset.ofTotalSeconds(0);
        Clock clock0 = MockClock.tickMinutes(zoneOffset0);
        Symmetry454Date symmetry454Date0 = Symmetry454Date.now(clock0);
        Symmetry454Chronology symmetry454Chronology0 = symmetry454Date0.getChronology();
        Symmetry454Date symmetry454Date1 = symmetry454Chronology0.dateNow(clock0);
        assertTrue(symmetry454Date1.equals((Object) symmetry454Date0));
    }
}
