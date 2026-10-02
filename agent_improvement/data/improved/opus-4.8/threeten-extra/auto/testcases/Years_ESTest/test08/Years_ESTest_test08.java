package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.Period;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.IsoChronology;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Years_ESTest_test08 extends Years_ESTest_scaffolding {

    /**
     * Verifies that {@code addTo} leaves the {@code Years} instance itself unchanged:
     * adding zero years to a date is a no-op on the amount, so the amount stays 0.
     */
    @Test(timeout = 4000)
    public void addToDoesNotMutateAmount() throws Throwable {
        Years zeroYears = Years.of(0);

        // An arbitrary ISO date to use as the target of the addition.
        IsoChronology isoChronology = Period.ofYears(-1).getChronology();
        ChronoLocalDate targetDate = isoChronology.dateEpochDay(-217L);

        zeroYears.addTo(targetDate);

        assertEquals(0, zeroYears.getAmount());
    }
}
