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
     * Comparing a DayOfMonth to itself yields zero, and the day extracted
     * from the mocked "current" OffsetDateTime is the 14th of the month.
     */
    @Test(timeout = 4000)
    public void compareToSelfReturnsZero() throws Throwable {
        OffsetDateTime mockedNow = MockOffsetDateTime.now();
        DayOfMonth dayOfMonth = DayOfMonth.from(mockedNow);

        int comparison = dayOfMonth.compareTo(dayOfMonth);

        assertEquals(0, comparison);
        assertEquals(14, dayOfMonth.getValue());
    }
}
