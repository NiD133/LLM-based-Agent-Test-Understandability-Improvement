package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
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

    @Test(timeout = 4000)
    public void test05() throws Throwable {
        // Retrieve IsoChronology from a period (same path as original test)
        Period period = Period.ofYears(-1);
        IsoChronology isoChronology = period.getChronology();

        // Create a date 217 days before the Unix epoch (1970-01-01)
        ChronoLocalDate dateBeforeEpoch = isoChronology.dateEpochDay(-217L);

        // Subtracting one year must return a new Temporal instance (Years is immutable)
        Temporal resultDate = Years.ONE.subtractFrom(dateBeforeEpoch);

        assertNotSame(resultDate, dateBeforeEpoch);
    }
}
