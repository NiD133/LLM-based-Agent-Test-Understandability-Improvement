package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.fail;
import static org.evosuite.runtime.EvoAssertions.verifyException;
import java.time.chrono.Era;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Symmetry010Chronology_ESTest_test16 extends Symmetry010Chronology_ESTest_scaffolding {

    /**
     * Building a date requires a non-null IsoEra. Passing a null era should make
     * {@code date} reject the era with a ClassCastException ("Invalid era: null"),
     * regardless of the year/month/day values supplied.
     */
    @Test(timeout = 4000)
    public void dateWithNullEraThrowsClassCastException() throws Throwable {
        Symmetry010Chronology chronology = Symmetry010Chronology.INSTANCE;

        try {
            chronology.date((Era) null, -1134, 2336, -1134);
            fail("Expected a ClassCastException because the era is null");
        } catch (ClassCastException expected) {
            // Message reads "Invalid era: null"
            verifyException("org.threeten.extra.chrono.Symmetry010Chronology", expected);
        }
    }
}
