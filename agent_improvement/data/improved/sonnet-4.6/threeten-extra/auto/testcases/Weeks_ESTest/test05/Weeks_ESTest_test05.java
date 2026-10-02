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

    // Subtracting zero weeks from a date is a no-op: the same Temporal object is returned.
    @Test(timeout = 4000)
    public void test05() throws Throwable {
        Weeks zeroWeeks = Weeks.ZERO;
        JapaneseDate today = MockJapaneseDate.now();
        Temporal result = zeroWeeks.subtractFrom(today);
        assertSame(today, result);
    }
}
