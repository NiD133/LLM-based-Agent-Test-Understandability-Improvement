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
public class JulianChronology_ESTest_test10 extends JulianChronology_ESTest_scaffolding {

    /**
     * {@code JulianChronology.date(Era, ...)} requires the era to be a
     * {@link JulianEra}. Passing an era from a different calendar system
     * (here {@link ThaiBuddhistEra#BE}) must be rejected with a
     * {@link ClassCastException}.
     */
    @Test(timeout = 4000)
    public void dateWithNonJulianEraThrowsClassCastException() throws Throwable {
        JulianChronology julianChronology = new JulianChronology();
        Era nonJulianEra = ThaiBuddhistEra.BE;

        try {
            julianChronology.date(nonJulianEra, 61, 2, 61);
            fail("Expected ClassCastException because the era is not a JulianEra");
        } catch (ClassCastException e) {
            // Message: "Era must be JulianEra"
            verifyException("org.threeten.extra.chrono.JulianChronology", e);
        }
    }
}
