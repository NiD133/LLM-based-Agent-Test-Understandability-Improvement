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

    @Test(timeout = 4000)
    public void test_dateYearDay_withNonPaxEra_throwsClassCastException() throws Throwable {
        PaxChronology chronology = PaxChronology.INSTANCE;
        // ThaiBuddhistEra is not a PaxEra, so passing it should trigger a ClassCastException
        Era nonPaxEra = ThaiBuddhistEra.BEFORE_BE;
        try {
            chronology.dateYearDay(nonPaxEra, (-1431655764), (-2843));
            fail("Expecting exception: ClassCastException");
        } catch (ClassCastException e) {
            //
            // Era must be PaxEra
            //
            verifyException("org.threeten.extra.chrono.PaxChronology", e);
        }
    }
}
