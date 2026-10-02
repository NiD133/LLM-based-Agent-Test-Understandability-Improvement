package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockClock;
import org.evosuite.runtime.mock.java.time.MockOffsetDateTime;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BritishCutoverChronology_ESTest_test09 extends BritishCutoverChronology_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test09() throws Throwable {
        BritishCutoverChronology chronology = new BritishCutoverChronology();
        long earliestSupportedProlepticYear = -999998L;

        boolean leapYear = chronology.isLeapYear(earliestSupportedProlepticYear);

        assertFalse(leapYear);
    }
}
