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
public class Years_ESTest_test26 extends Years_ESTest_scaffolding {

    /**
     * Verifies that the number of years between a date and itself is zero,
     * since the span between identical start and end dates is empty.
     */
    @Test(timeout = 4000)
    public void between_sameDate_returnsZeroYears() throws Throwable {
        ThaiBuddhistDate sameDate = MockThaiBuddhistDate.now();

        Years yearsBetween = Years.between(sameDate, sameDate);

        assertEquals(0, yearsBetween.getAmount());
    }
}
