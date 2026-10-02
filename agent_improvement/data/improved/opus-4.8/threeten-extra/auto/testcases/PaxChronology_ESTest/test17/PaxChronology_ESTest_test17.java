package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.chrono.Era;
import java.time.chrono.ThaiBuddhistEra;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PaxChronology_ESTest_test17 extends PaxChronology_ESTest_scaffolding {

    /**
     * dateYearDay(Era, year, day) requires the era to be a PaxEra.
     * Passing an era from a different calendar system (here a ThaiBuddhistEra)
     * must be rejected with a ClassCastException thrown by PaxChronology.
     */
    @Test(timeout = 4000)
    public void dateYearDayWithNonPaxEraThrowsClassCastException() throws Throwable {
        PaxChronology paxChronology = new PaxChronology();
        Era nonPaxEra = ThaiBuddhistEra.BEFORE_BE;
        int yearOfEra = -1431655764;
        int dayOfYear = -2843;

        try {
            paxChronology.dateYearDay(nonPaxEra, yearOfEra, dayOfYear);
            fail("Expecting exception: ClassCastException because the era is not a PaxEra");
        } catch (ClassCastException e) {
            // Message: "Era must be PaxEra"
            verifyException("org.threeten.extra.chrono.PaxChronology", e);
        }
    }
}
