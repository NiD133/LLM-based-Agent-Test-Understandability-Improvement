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
public class Quarter_ESTest_test11 extends Quarter_ESTest_scaffolding {

    /**
     * A Quarter only supports the QUARTER_OF_YEAR field. Querying it for any other
     * ChronoField (here SECOND_OF_DAY) must fail with an
     * UnsupportedTemporalTypeException thrown from Quarter.getLong.
     */
    @Test(timeout = 4000)
    public void gettingUnsupportedChronoFieldFromQuarterThrows() throws Throwable {
        Quarter quarter = Quarter.Q4;
        ChronoField unsupportedField = ChronoField.SECOND_OF_DAY;

        try {
            // ChronoField.getFrom delegates to Quarter.getLong, which rejects this field.
            unsupportedField.getFrom(quarter);
            fail("Expected UnsupportedTemporalTypeException for unsupported field: SecondOfDay");
        } catch (UnsupportedTemporalTypeException expected) {
            verifyException("org.threeten.extra.Quarter", expected);
        }
    }
}
