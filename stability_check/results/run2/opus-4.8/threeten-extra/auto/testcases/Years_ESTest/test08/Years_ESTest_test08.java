package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
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
     * Adding a zero-year amount to a date leaves the {@link Years} instance
     * unchanged, since {@code Years} is immutable and its amount stays zero.
     */
    @Test(timeout = 4000)
    public void addingZeroYearsToDateLeavesAmountUnchanged() throws Throwable {
        Years zeroYears = Years.of(0);

        ChronoLocalDate date = IsoChronology.INSTANCE.dateEpochDay(-217L);
        zeroYears.addTo(date);

        assertEquals(0, zeroYears.getAmount());
    }
}
