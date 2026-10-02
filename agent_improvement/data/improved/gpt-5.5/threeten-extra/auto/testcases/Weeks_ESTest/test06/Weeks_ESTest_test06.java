package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.Period;
import java.time.temporal.Temporal;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockLocalDate;
import org.evosuite.runtime.mock.java.time.chrono.MockHijrahDate;
import org.evosuite.runtime.mock.java.time.chrono.MockJapaneseDate;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Weeks_ESTest_test06 extends Weeks_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test06() throws Throwable {
        Period oneWeekPeriod = Period.ofWeeks(1);
        Weeks oneWeek = Weeks.from(oneWeekPeriod);

        try {
            oneWeek.addTo((Temporal) null);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException expectedException) {
            verifyException("org.threeten.extra.Weeks", expectedException);
        }
    }
}
