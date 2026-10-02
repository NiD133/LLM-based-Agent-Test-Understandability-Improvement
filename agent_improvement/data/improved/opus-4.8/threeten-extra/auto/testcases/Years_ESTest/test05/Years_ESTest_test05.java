package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.Period;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.IsoChronology;
import java.time.temporal.Temporal;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Years_ESTest_test05 extends Years_ESTest_scaffolding {

    /**
     * Verifies that subtracting one year from a date returns a new, distinct
     * temporal instance rather than mutating or returning the original date.
     */
    @Test(timeout = 4000)
    public void subtractFromDateReturnsDistinctInstance() throws Throwable {
        // Build an ISO date from an arbitrary epoch day to act as the target temporal.
        IsoChronology isoChronology = Period.ofYears(-1).getChronology();
        ChronoLocalDate originalDate = isoChronology.dateEpochDay(-217L);

        // Subtract one year from the date.
        Temporal adjustedDate = Years.ONE.subtractFrom(originalDate);

        // The adjustment must produce a different object than the input date.
        assertNotSame(adjustedDate, originalDate);
    }
}
