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
     * Verifies that dateYearDay(Era, int, int) throws ClassCastException
     * when the supplied era is not an InternationalFixedEra (e.g. ThaiBuddhistEra).
     */
    @Test(timeout = 4000)
    public void test17() throws Throwable {
        ThaiBuddhistEra incompatibleEra = ThaiBuddhistEra.BEFORE_BE;
        try {
            InternationalFixedChronology.INSTANCE.dateYearDay((Era) incompatibleEra, 4, 4);
            fail("Expecting exception: ClassCastException");
        } catch (ClassCastException e) {
            verifyException("org.threeten.extra.chrono.InternationalFixedChronology", e);
        }
    }
}
