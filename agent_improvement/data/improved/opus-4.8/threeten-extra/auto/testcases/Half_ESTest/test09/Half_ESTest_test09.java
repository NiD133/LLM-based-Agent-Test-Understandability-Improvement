package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.fail;
import static org.evosuite.runtime.EvoAssertions.verifyException;
import java.time.temporal.ChronoField;
import java.time.temporal.UnsupportedTemporalTypeException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Half_ESTest_test09 extends Half_ESTest_scaffolding {

    /**
     * A {@link Half} only supports the HALF_OF_YEAR field. Querying it for any
     * standard {@link ChronoField} (here OFFSET_SECONDS) must fail with an
     * {@link UnsupportedTemporalTypeException}, thrown from within Half.getLong.
     */
    @Test(timeout = 4000)
    public void getFromUnsupportedChronoFieldThrows() throws Throwable {
        Half half = Half.H2;
        ChronoField unsupportedField = ChronoField.OFFSET_SECONDS;

        try {
            // ChronoField.getFrom delegates to Half.getLong, which rejects the field.
            unsupportedField.getFrom(half);
            fail("Expecting exception: UnsupportedTemporalTypeException");
        } catch (UnsupportedTemporalTypeException e) {
            // Message: "Unsupported field: OffsetSeconds"
            verifyException("org.threeten.extra.Half", e);
        }
    }
}
