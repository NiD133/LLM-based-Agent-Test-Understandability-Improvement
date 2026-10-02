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

    @Test(timeout = 4000)
    public void test_compareToSelf_returnsZero() throws Throwable {
        // MockOffsetDateTime.now() returns a fixed date-time where the day-of-month is 14
        OffsetDateTime fixedNow = MockOffsetDateTime.now();
        DayOfMonth dayOfMonth14 = DayOfMonth.from(fixedNow);

        int comparisonResult = dayOfMonth14.compareTo(dayOfMonth14);

        assertEquals("A DayOfMonth compared to itself should return 0", 0, comparisonResult);
        assertEquals("The day-of-month value extracted from the fixed mock date should be 14", 14, dayOfMonth14.getValue());
    }
}
