package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.DateTimeException;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AmPm_ESTest_test12 extends AmPm_ESTest_scaffolding {

    /**
     * AmPm.of(int) only accepts 0 (AM) and 1 (PM); any other value is rejected.
     * Here 36 is out of range, so the factory must throw a DateTimeException.
     */
    @Test(timeout = 4000)
    public void of_withValueOutOfRange_throwsDateTimeException() throws Throwable {
        int invalidAmPmValue = 36;
        try {
            AmPm.of(invalidAmPmValue);
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException e) {
            // Message reads "Invalid value for AM/PM: 36"
            verifyException("org.threeten.extra.AmPm", e);
        }
    }
}
