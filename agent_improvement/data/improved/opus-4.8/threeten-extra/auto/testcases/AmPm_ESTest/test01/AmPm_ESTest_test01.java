package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.temporal.ChronoField;
import java.time.temporal.UnsupportedTemporalTypeException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AmPm_ESTest_test01 extends AmPm_ESTest_scaffolding {

    /**
     * AmPm only supports the AMPM_OF_DAY field. Reading any other ChronoField
     * (here ERA) must fail, so {@code ERA.getFrom(AM)} — which delegates to
     * {@code AmPm.getLong(ERA)} — is expected to throw
     * UnsupportedTemporalTypeException.
     */
    @Test(timeout = 4000)
    public void readingUnsupportedEraFieldThrows() throws Throwable {
        AmPm morning = AmPm.AM;
        ChronoField unsupportedField = ChronoField.ERA;

        try {
            unsupportedField.getFrom(morning);
            fail("Expected UnsupportedTemporalTypeException for the unsupported ERA field");
        } catch (UnsupportedTemporalTypeException expected) {
            // Message reads "Unsupported field: Era" and is thrown by AmPm.
            verifyException("org.threeten.extra.AmPm", expected);
        }
    }
}
