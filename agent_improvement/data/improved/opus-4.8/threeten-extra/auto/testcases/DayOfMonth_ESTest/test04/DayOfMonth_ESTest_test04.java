package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
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
     * Verifies that:
     * <ul>
     *   <li>{@link DayOfMonth#from} extracts the day-of-month from a temporal
     *       (here the mocked "current" date, which is the 14th), and</li>
     *   <li>{@link DayOfMonth#equals} returns false when compared with an object
     *       that is not a {@code DayOfMonth} (a {@link ChronoField}).</li>
     * </ul>
     */
    @Test(timeout = 4000)
    public void from_extractsDayOfMonth_andEqualsRejectsNonDayOfMonth() throws Throwable {
        OffsetDateTime mockedNow = MockOffsetDateTime.now();
        DayOfMonth dayOfMonth = DayOfMonth.from(mockedNow);

        boolean equalsField = dayOfMonth.equals(ChronoField.MICRO_OF_SECOND);

        assertFalse("A DayOfMonth must not be equal to a ChronoField", equalsField);
        assertEquals("Mocked current day-of-month should be the 14th", 14, dayOfMonth.getValue());
    }
}
