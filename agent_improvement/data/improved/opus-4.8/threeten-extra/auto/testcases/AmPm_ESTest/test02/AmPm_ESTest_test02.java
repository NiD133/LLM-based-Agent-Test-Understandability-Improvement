package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.time.temporal.ChronoField;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AmPm_ESTest_test02 extends AmPm_ESTest_scaffolding {

    /**
     * Verifies that querying the PM constant for the AMPM_OF_DAY field
     * returns 1, the numeric value assigned to PM.
     */
    @Test(timeout = 4000)
    public void getAmPmOfDayFieldForPmReturnsOne() throws Throwable {
        int valueForPm = AmPm.PM.get(ChronoField.AMPM_OF_DAY);

        assertEquals(1, valueForPm);
    }
}
