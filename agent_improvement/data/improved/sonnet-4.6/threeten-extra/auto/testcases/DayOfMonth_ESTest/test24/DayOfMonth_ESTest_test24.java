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

    // Verifies that a DayOfMonth derived from an OffsetDateTime compares equal to itself,
    // and that the mocked "current" day is the 14th.
    @Test(timeout = 4000)
    public void test24_compareToSelf_returnsZero() throws Throwable {
        OffsetDateTime now = MockOffsetDateTime.now();
        DayOfMonth dayOfMonth = DayOfMonth.from(now);

        int comparisonResult = dayOfMonth.compareTo(dayOfMonth);

        assertEquals(0, comparisonResult);
        assertEquals(14, dayOfMonth.getValue());
    }
}
