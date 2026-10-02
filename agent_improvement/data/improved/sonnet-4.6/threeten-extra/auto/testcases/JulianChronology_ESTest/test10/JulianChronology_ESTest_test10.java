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

    @Test(timeout = 4000)
    public void testDateThrowsClassCastExceptionWhenEraIsNotJulianEra() throws Throwable {
        JulianChronology chronology = new JulianChronology();
        ThaiBuddhistEra wrongEra = ThaiBuddhistEra.BE;
        try {
            chronology.date((Era) wrongEra, 61, 2, 61);
            fail("Expecting exception: ClassCastException");
        } catch (ClassCastException e) {
            verifyException("org.threeten.extra.chrono.JulianChronology", e);
        }
    }
}
