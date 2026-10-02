package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.fail;

import java.time.temporal.ChronoField;
import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Half_ESTest_test11 extends Half_ESTest_scaffolding {

    /**
     * Half.range only supports the HALF_OF_YEAR field; any ChronoField is rejected.
     * Here ALIGNED_DAY_OF_WEEK_IN_YEAR is a ChronoField, so range must throw
     * UnsupportedTemporalTypeException.
     */
    @Test(timeout = 4000)
    public void rangeWithUnsupportedChronoFieldThrows() throws Throwable {
        Half half = Half.H2;
        ChronoField unsupportedField = ChronoField.ALIGNED_DAY_OF_WEEK_IN_YEAR;

        try {
            half.range(unsupportedField);
            fail("Expected UnsupportedTemporalTypeException for an unsupported ChronoField");
        } catch (UnsupportedTemporalTypeException expected) {
            // Message: "Unsupported field: AlignedDayOfWeekInYear"
        }
    }
}
