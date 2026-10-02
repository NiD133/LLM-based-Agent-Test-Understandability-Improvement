package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.DateTimeException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class InternationalFixedChronology_ESTest_test18 extends InternationalFixedChronology_ESTest_scaffolding {

    // The International Fixed calendar has only one valid era (CE = 1).
    // Negative era values should be rejected with a DateTimeException.
    @Test(timeout = 4000)
    public void test_eraOf_withInvalidNegativeValue_throwsDateTimeException() throws Throwable {
        InternationalFixedChronology chronology = new InternationalFixedChronology();
        try {
            chronology.INSTANCE.eraOf(-2771);
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException e) {
            //
            // Invalid era: -2771
            //
            verifyException("org.threeten.extra.chrono.InternationalFixedEra", e);
        }
    }
}
