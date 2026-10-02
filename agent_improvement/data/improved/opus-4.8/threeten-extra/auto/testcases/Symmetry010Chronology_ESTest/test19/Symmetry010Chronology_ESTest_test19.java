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
public class Symmetry010Chronology_ESTest_test19 extends Symmetry010Chronology_ESTest_scaffolding {

    /**
     * Verifies that {@link Symmetry010Chronology#eraOf(int)} rejects an era value
     * outside the valid ISO era range (0 = BCE, 1 = CE).
     * <p>
     * The lookup is delegated to {@link java.time.chrono.IsoEra#of(int)}, which
     * throws a {@link DateTimeException} for any unsupported value such as -517.
     */
    @Test(timeout = 4000)
    public void eraOf_withInvalidEraValue_throwsDateTimeException() throws Throwable {
        Symmetry010Chronology chronology = new Symmetry010Chronology();
        int invalidEraValue = -517;

        try {
            chronology.eraOf(invalidEraValue);
            fail("Expected DateTimeException for invalid era value: " + invalidEraValue);
        } catch (DateTimeException e) {
            // The era value is validated by IsoEra.of, which rejects -517 as an "Invalid era".
            verifyException("java.time.chrono.IsoEra", e);
        }
    }
}
