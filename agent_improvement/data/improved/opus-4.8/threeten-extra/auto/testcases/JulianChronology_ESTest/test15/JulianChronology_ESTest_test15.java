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
public class JulianChronology_ESTest_test15 extends JulianChronology_ESTest_scaffolding {

    /**
     * Verifies that {@link JulianChronology#eraOf(int)} rejects an era value that
     * does not correspond to one of the two Julian eras (BC and AD).
     * The lookup is delegated to {@code JulianEra.of(int)}, which throws a
     * {@link DateTimeException} for any invalid era value.
     */
    @Test(timeout = 4000)
    public void eraOf_withInvalidEraValue_throwsDateTimeException() throws Throwable {
        JulianChronology julianChronology = new JulianChronology();
        int invalidEraValue = -1073741823;

        try {
            julianChronology.eraOf(invalidEraValue);
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException e) {
            // Invalid era: -1073741823
            verifyException("org.threeten.extra.chrono.JulianEra", e);
        }
    }
}
