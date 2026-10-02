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
     * Passing an era from a foreign calendar system (JapaneseEra) to
     * {@code date(Era, int, int, int)} must throw ClassCastException because
     * the method requires an {@code InternationalFixedEra} argument.
     */
    @Test(timeout = 4000)
    public void test23() throws Throwable {
        InternationalFixedChronology chronology = new InternationalFixedChronology();
        Era incompatibleEra = JapaneseEra.SHOWA;

        try {
            chronology.date(incompatibleEra, -1, -1, -1);
            fail("Expecting exception: ClassCastException");
        } catch (ClassCastException e) {
            verifyException("org.threeten.extra.chrono.InternationalFixedChronology", e);
        }
    }
}
