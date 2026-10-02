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
     * eraOf() delegates to IsoEra.of(), which only accepts 0 (BCE) or 1 (CE).
     * Any other value must throw DateTimeException with the message "Invalid era: <value>".
     */
    @Test(timeout = 4000)
    public void test19_eraOf_withOutOfRangeValue_throwsDateTimeException() throws Throwable {
        Symmetry010Chronology chronology = new Symmetry010Chronology();

        try {
            chronology.eraOf(-517);
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException e) {
            // Exception message is "Invalid era: -517", thrown by IsoEra.of()
            verifyException("java.time.chrono.IsoEra", e);
        }
    }
}
