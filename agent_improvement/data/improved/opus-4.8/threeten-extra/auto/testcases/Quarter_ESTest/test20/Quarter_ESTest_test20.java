package org.threeten.extra;

import static org.junit.Assert.fail;
import static org.evosuite.runtime.EvoAssertions.verifyException;

import java.time.DateTimeException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Quarter_ESTest_test20 extends Quarter_ESTest_scaffolding {

    /**
     * Quarter.of(int) only accepts values 1..4 (Q1..Q4). Any value outside that
     * range must be rejected with a DateTimeException. Here 6 is out of range,
     * so the factory is expected to throw.
     */
    @Test(timeout = 4000)
    public void of_withValueAboveFour_throwsDateTimeException() throws Throwable {
        int outOfRangeQuarter = 6;
        try {
            Quarter.of(outOfRangeQuarter);
            fail("Expected DateTimeException for invalid quarter value: " + outOfRangeQuarter);
        } catch (DateTimeException expected) {
            // Message produced by Quarter: "Invalid value for Quarter: 6"
            verifyException("org.threeten.extra.Quarter", expected);
        }
    }
}
