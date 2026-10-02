package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.ZonedDateTime;
import java.time.temporal.Temporal;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockZonedDateTime;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Minutes_ESTest_test05 extends Minutes_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void testAddToReturnsNewTemporalAndOfHoursConvertsToMinutes() throws Throwable {
        // 3 hours expressed as minutes (3 * 60 = 180)
        Minutes threeHoursInMinutes = Minutes.ofHours(3);

        ZonedDateTime now = MockZonedDateTime.now();
        Temporal adjustedDateTime = threeHoursInMinutes.addTo(now);

        // addTo with a non-zero amount must return a new temporal object
        assertNotSame(adjustedDateTime, now);
        // ofHours(3) should store 180 as the minute count
        assertEquals(180, threeHoursInMinutes.getAmount());
    }
}
