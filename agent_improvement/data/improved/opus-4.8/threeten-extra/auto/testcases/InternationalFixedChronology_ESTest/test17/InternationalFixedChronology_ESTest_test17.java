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
public class InternationalFixedChronology_ESTest_test17 extends InternationalFixedChronology_ESTest_scaffolding {

    /**
     * dateYearDay must reject an era that is not an InternationalFixedEra.
     * Passing a ThaiBuddhistEra triggers a ClassCastException reporting the
     * invalid era ("Invalid era: BEFORE_BE").
     */
    @Test(timeout = 4000)
    public void dateYearDay_withForeignEra_throwsClassCastException() throws Throwable {
        InternationalFixedChronology chronology = InternationalFixedChronology.INSTANCE;
        Era foreignEra = ThaiBuddhistEra.BEFORE_BE;

        try {
            chronology.dateYearDay(foreignEra, 4, 4);
            fail("Expecting exception: ClassCastException");
        } catch (ClassCastException e) {
            // Invalid era: BEFORE_BE
            verifyException("org.threeten.extra.chrono.InternationalFixedChronology", e);
        }
    }
}
