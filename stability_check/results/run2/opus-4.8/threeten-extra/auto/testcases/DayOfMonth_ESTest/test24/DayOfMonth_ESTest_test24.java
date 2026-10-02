package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.OffsetDateTime;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockOffsetDateTime;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DayOfMonth_ESTest_test24 extends DayOfMonth_ESTest_scaffolding {

    /**
     * Comparing a DayOfMonth to itself should report equality (a comparison
     * result of 0). The DayOfMonth is derived from the mocked "current" date,
     * whose day-of-month is fixed at 14.
     */
    @Test(timeout = 4000)
    public void compareToSelfReturnsZero() throws Throwable {
        OffsetDateTime currentDateTime = MockOffsetDateTime.now();
        DayOfMonth dayOfMonth = DayOfMonth.from(currentDateTime);

        int comparisonResult = dayOfMonth.compareTo(dayOfMonth);

        assertEquals(0, comparisonResult);
        assertEquals(14, dayOfMonth.getValue());
    }
}
