package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.CharBuffer;
import java.time.DateTimeException;
import java.time.Duration;
import java.time.Period;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.IsoChronology;
import java.time.chrono.ThaiBuddhistDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.time.temporal.UnsupportedTemporalTypeException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.chrono.MockThaiBuddhistDate;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Years_ESTest_test08 extends Years_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test08() throws Throwable {
        // Create a Years instance representing zero years
        Years zeroYears = Years.of(0);

        // Obtain an IsoChronology instance via a Period (required by EvoSuite's mock framework)
        Period periodOfMinusOneYear = Period.ofYears((-1));
        IsoChronology isoChronology = periodOfMinusOneYear.getChronology();

        // Resolve a local date 217 days before the Unix epoch (approximately 1969-05-29)
        ChronoLocalDate dateBeforeEpoch = isoChronology.dateEpochDay((-217L));

        // Adding zero years to a date is a no-op; the Years instance itself is unchanged
        zeroYears.addTo(dateBeforeEpoch);

        // The amount stored in zeroYears must still be 0 after the addTo call
        assertEquals(0, zeroYears.getAmount());
    }
}
