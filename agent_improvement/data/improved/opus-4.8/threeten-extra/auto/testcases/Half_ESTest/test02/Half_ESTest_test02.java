package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.OffsetDateTime;
import java.time.temporal.Temporal;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockOffsetDateTime;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Half_ESTest_test02 extends Half_ESTest_scaffolding {

    /**
     * Adjusting a date-time to half H2 (July-December) shifts an H1 date by six
     * months, so {@code adjustInto} must return a new, distinct temporal object
     * rather than the one passed in.
     */
    @Test(timeout = 4000)
    public void adjustIntoReturnsNewTemporalInstance() throws Throwable {
        Half secondHalf = Half.H2;
        OffsetDateTime originalDateTime = MockOffsetDateTime.now();

        Temporal adjustedDateTime = secondHalf.adjustInto(originalDateTime);

        assertNotSame(adjustedDateTime, originalDateTime);
    }
}
