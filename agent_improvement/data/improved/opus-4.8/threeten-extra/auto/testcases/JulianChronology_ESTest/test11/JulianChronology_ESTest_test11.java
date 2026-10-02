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
public class JulianChronology_ESTest_test11 extends JulianChronology_ESTest_scaffolding {

    /**
     * {@code dateYearDay} must reject an era that is not a {@code JulianEra}.
     * Passing a {@code ThaiBuddhistEra} should trigger the era-type check in
     * {@code prolepticYear}, which throws a {@code ClassCastException}
     * with the message "Era must be JulianEra".
     */
    @Test(timeout = 4000)
    public void dateYearDay_withNonJulianEra_throwsClassCastException() throws Throwable {
        JulianChronology julianChronology = JulianChronology.INSTANCE;
        Era nonJulianEra = ThaiBuddhistEra.BEFORE_BE;

        try {
            julianChronology.dateYearDay(nonJulianEra, 3, 3);
            fail("Expecting exception: ClassCastException");
        } catch (ClassCastException e) {
            // Era must be JulianEra
            verifyException("org.threeten.extra.chrono.JulianChronology", e);
        }
    }
}
