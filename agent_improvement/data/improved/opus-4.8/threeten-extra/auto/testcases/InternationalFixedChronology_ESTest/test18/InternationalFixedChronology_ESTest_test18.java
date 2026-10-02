package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.fail;
import static org.evosuite.runtime.EvoAssertions.verifyException;
import java.time.DateTimeException;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class InternationalFixedChronology_ESTest_test18 extends InternationalFixedChronology_ESTest_scaffolding {

    /**
     * eraOf() rejects values that do not correspond to a valid International Fixed era,
     * throwing a DateTimeException (raised by InternationalFixedEra) for the invalid value -2771.
     */
    @Test(timeout = 4000)
    public void eraOf_withInvalidEraValue_throwsDateTimeException() throws Throwable {
        int invalidEraValue = -2771;

        try {
            InternationalFixedChronology.INSTANCE.eraOf(invalidEraValue);
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException e) {
            // Invalid era: -2771
            verifyException("org.threeten.extra.chrono.InternationalFixedEra", e);
        }
    }
}
