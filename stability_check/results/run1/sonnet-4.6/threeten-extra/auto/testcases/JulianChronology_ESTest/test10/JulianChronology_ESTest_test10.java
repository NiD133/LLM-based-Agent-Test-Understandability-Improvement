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
     * Verifies that passing a non-JulianEra (ThaiBuddhistEra) to date(Era, ...) throws ClassCastException,
     * because JulianChronology only accepts JulianEra instances.
     */
    @Test(timeout = 4000)
    public void test_date_withNonJulianEra_throwsClassCastException() throws Throwable {
        JulianChronology julianChronology = new JulianChronology();
        Era thaiBuddhistEra = ThaiBuddhistEra.BE;

        try {
            julianChronology.date(thaiBuddhistEra, 61, 2, 61);
            fail("Expecting exception: ClassCastException");
        } catch (ClassCastException e) {
            verifyException("org.threeten.extra.chrono.JulianChronology", e);
        }
    }
}
