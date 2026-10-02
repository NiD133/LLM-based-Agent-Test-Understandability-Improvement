package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.fail;

import java.time.Month;
import java.time.temporal.ChronoField;
import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import static org.evosuite.runtime.EvoAssertions.verifyException;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Half_ESTest_test20 extends Half_ESTest_scaffolding {

    /**
     * {@code Half.get(TemporalField)} only supports the HALF_OF_YEAR field.
     * Querying it with any {@link ChronoField} (here DAY_OF_YEAR) must throw
     * {@link UnsupportedTemporalTypeException}.
     */
    @Test(timeout = 4000)
    public void get_withUnsupportedChronoField_throwsException() throws Throwable {
        Half secondHalf = Half.from(Month.OCTOBER);

        try {
            secondHalf.get(ChronoField.DAY_OF_YEAR);
            fail("Expected UnsupportedTemporalTypeException for unsupported field DayOfYear");
        } catch (UnsupportedTemporalTypeException expected) {
            // Half.get rejects ChronoField values with "Unsupported field: DayOfYear"
            verifyException("org.threeten.extra.Half", expected);
        }
    }
}
