package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import java.time.DateTimeException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

/**
 * Verifies that {@link Half#of(int)} rejects values outside the valid range of 1 (H1) to 2 (H2).
 */
@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Half_ESTest_test19 extends Half_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void of_withZero_throwsDateTimeException() throws Throwable {
        try {
            Half.of(0);
            fail("Expected a DateTimeException because 0 is not a valid half-of-year");
        } catch (DateTimeException expected) {
            assertEquals("Invalid value for Half: 0", expected.getMessage());
        }
    }
}
