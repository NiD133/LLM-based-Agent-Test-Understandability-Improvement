package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.chrono.Era;
import java.time.chrono.JapaneseEra;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class InternationalFixedChronology_ESTest_test23 extends InternationalFixedChronology_ESTest_scaffolding {

    /**
     * {@link InternationalFixedChronology#date(Era, int, int, int)} requires the era to be an
     * {@link InternationalFixedEra}. Supplying an era from a different calendar system (here a
     * {@link JapaneseEra}) must be rejected with a {@link ClassCastException}.
     */
    @Test(timeout = 4000)
    public void date_withForeignEra_throwsClassCastException() throws Throwable {
        InternationalFixedChronology chronology = new InternationalFixedChronology();
        Era foreignEra = JapaneseEra.SHOWA;

        try {
            chronology.date(foreignEra, -1, -1, -1);
            fail("Expecting exception: ClassCastException");
        } catch (ClassCastException e) {
            // Invalid era: Showa is not an InternationalFixedEra
            verifyException("org.threeten.extra.chrono.InternationalFixedChronology", e);
        }
    }
}
