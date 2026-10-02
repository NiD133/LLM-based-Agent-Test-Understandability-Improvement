package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.chrono.ThaiBuddhistDate;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.chrono.MockThaiBuddhistDate;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Months_ESTest_test28 extends Months_ESTest_scaffolding {

    /**
     * The number of months between a date and itself should be zero,
     * since the period start and end are identical.
     */
    @Test(timeout = 4000)
    public void between_sameDate_returnsZeroMonths() throws Throwable {
        ThaiBuddhistDate today = MockThaiBuddhistDate.now();

        Months monthsBetween = Months.between(today, today);

        assertTrue(monthsBetween.isZero());
    }
}
