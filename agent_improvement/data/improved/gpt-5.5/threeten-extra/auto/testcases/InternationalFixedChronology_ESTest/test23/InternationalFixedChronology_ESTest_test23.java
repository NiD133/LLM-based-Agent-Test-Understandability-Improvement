package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.chrono.Era;
import java.time.chrono.JapaneseEra;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockClock;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class InternationalFixedChronology_ESTest_test23 extends InternationalFixedChronology_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test23() throws Throwable {
        InternationalFixedChronology chronology = new InternationalFixedChronology();
        JapaneseEra nonInternationalFixedEra = JapaneseEra.SHOWA;

        try {
            chronology.date((Era) nonInternationalFixedEra, (-1), (-1), (-1));
            fail("Expecting exception: ClassCastException");
        } catch (ClassCastException exception) {
            verifyException("org.threeten.extra.chrono.InternationalFixedChronology", exception);
        }
    }
}
