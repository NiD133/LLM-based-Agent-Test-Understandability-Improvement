package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.chrono.JapaneseDate;
import java.time.temporal.Temporal;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.chrono.MockJapaneseDate;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Weeks_ESTest_test04 extends Weeks_ESTest_scaffolding {

    /**
     * Subtracting a non-zero {@code Weeks} amount from a temporal must produce a
     * new, adjusted temporal rather than returning the original instance.
     */
    @Test(timeout = 4000)
    public void subtractFrom_oneWeek_returnsDifferentTemporal() throws Throwable {
        Weeks oneWeek = Weeks.ONE;
        JapaneseDate today = MockJapaneseDate.now();

        Temporal oneWeekEarlier = oneWeek.subtractFrom(today);

        assertNotSame(oneWeekEarlier, today);
    }
}
