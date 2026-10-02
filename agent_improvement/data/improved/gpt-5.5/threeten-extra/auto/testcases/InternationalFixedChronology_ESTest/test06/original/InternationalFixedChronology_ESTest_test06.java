package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.Clock;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.chrono.ChronoZonedDateTime;
import java.time.chrono.Era;
import java.time.chrono.JapaneseEra;
import java.time.chrono.ThaiBuddhistEra;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalField;
import java.time.temporal.ValueRange;
import java.util.List;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockClock;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class InternationalFixedChronology_ESTest_test06 extends InternationalFixedChronology_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test06() throws Throwable {
        ZoneOffset zoneOffset0 = ZoneOffset.UTC;
        InternationalFixedDate internationalFixedDate0 = InternationalFixedDate.now((ZoneId) zoneOffset0);
        ChronoField chronoField0 = ChronoField.ERA;
        // Undeclared exception!
        try {
            internationalFixedDate0.with((TemporalField) chronoField0, 308L);
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException e) {
            //
            // Invalid value for Era (valid values 1 - 1): 308
            //
            verifyException("java.time.temporal.ValueRange", e);
        }
    }
}
