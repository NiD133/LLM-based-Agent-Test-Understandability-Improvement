package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.fail;
import static org.evosuite.runtime.EvoAssertions.verifyException;
import java.time.DateTimeException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Symmetry454Chronology_ESTest_test14 extends Symmetry454Chronology_ESTest_scaffolding {

    /**
     * eraOf delegates to IsoEra.of, which only accepts the values 0 (BCE) and 1 (CE).
     * Any other era value must be rejected with a DateTimeException.
     */
    @Test(timeout = 4000)
    public void eraOf_withInvalidEraValue_throwsDateTimeException() throws Throwable {
        Symmetry454Chronology chronology = new Symmetry454Chronology();
        int invalidEraValue = 107016;

        try {
            chronology.INSTANCE.eraOf(invalidEraValue);
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException e) {
            // Invalid era: 107016 — rejected by java.time.chrono.IsoEra
            verifyException("java.time.chrono.IsoEra", e);
        }
    }
}
