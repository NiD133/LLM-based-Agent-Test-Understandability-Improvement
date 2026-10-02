package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoField;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockOffsetDateTime;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DayOfMonth_ESTest_test04 extends DayOfMonth_ESTest_scaffolding {

    /**
     * Verifies that DayOfMonth.equals() returns false when compared to a non-DayOfMonth
     * object (a ChronoField enum constant), and that the day-of-month value extracted
     * from a mocked OffsetDateTime is 14.
     */
    @Test(timeout = 4000)
    public void test04() throws Throwable {
        OffsetDateTime mockedNow = MockOffsetDateTime.now();
        DayOfMonth dayOfMonth = DayOfMonth.from(mockedNow);

        ChronoField microOfSecondField = ChronoField.MICRO_OF_SECOND;
        boolean isEqualToChronoField = dayOfMonth.equals(microOfSecondField);

        assertFalse(isEqualToChronoField);
        assertEquals(14, dayOfMonth.getValue());
    }
}
