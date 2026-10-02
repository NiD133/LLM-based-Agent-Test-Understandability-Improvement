package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.fail;
import static org.evosuite.runtime.EvoAssertions.verifyException;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalUnit;
import java.time.temporal.UnsupportedTemporalTypeException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Months_ESTest_test18 extends Months_ESTest_scaffolding {

    /**
     * Months.get(TemporalUnit) only supports the MONTHS unit. Querying it with
     * any other unit must throw UnsupportedTemporalTypeException. Here the unit
     * used is WEEKS (the base unit of the ALIGNED_WEEK_OF_MONTH field).
     */
    @Test(timeout = 4000)
    public void get_withUnsupportedUnit_throwsUnsupportedTemporalTypeException() throws Throwable {
        Months oneMonth = Months.ONE;
        TemporalUnit weeksUnit = ChronoField.ALIGNED_WEEK_OF_MONTH.getBaseUnit();

        try {
            oneMonth.get(weeksUnit);
            fail("Expecting exception: UnsupportedTemporalTypeException");
        } catch (UnsupportedTemporalTypeException e) {
            // Message: "Unsupported unit: Weeks"
            verifyException("org.threeten.extra.Months", e);
        }
    }
}
