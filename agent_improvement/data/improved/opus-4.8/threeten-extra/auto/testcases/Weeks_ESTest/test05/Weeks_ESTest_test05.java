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
public class Weeks_ESTest_test05 extends Weeks_ESTest_scaffolding {

    /**
     * Subtracting a zero-week amount from a temporal must leave it unchanged.
     * Because {@code Weeks.ZERO} represents no weeks, {@code subtractFrom}
     * should return the very same date instance it was given.
     */
    @Test(timeout = 4000)
    public void subtractingZeroWeeksReturnsSameTemporal() throws Throwable {
        Weeks zeroWeeks = Weeks.ZERO;
        JapaneseDate originalDate = MockJapaneseDate.now();

        Temporal result = zeroWeeks.subtractFrom(originalDate);

        assertSame(originalDate, result);
    }
}
