package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.fail;
import static org.evosuite.runtime.EvoAssertions.verifyException;

import java.time.Duration;
import java.time.temporal.UnsupportedTemporalTypeException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Years_ESTest_test20 extends Years_ESTest_scaffolding {

    /**
     * {@code Duration.from} only accepts amounts whose units have an exact
     * (non-estimated) duration. The {@code Years} amount is measured in the
     * YEARS unit, which has an estimated duration, so converting it to a
     * {@code Duration} must fail with an {@link UnsupportedTemporalTypeException}.
     */
    @Test(timeout = 4000)
    public void durationFromYearsThrowsBecauseYearsHasEstimatedDuration() throws Throwable {
        Years oneYear = Years.ONE;

        try {
            Duration.from(oneYear);
            fail("Expected UnsupportedTemporalTypeException: a year has an estimated duration");
        } catch (UnsupportedTemporalTypeException expected) {
            // Thrown by Duration when the temporal amount uses an estimated-duration unit.
            verifyException("java.time.Duration", expected);
        }
    }
}
