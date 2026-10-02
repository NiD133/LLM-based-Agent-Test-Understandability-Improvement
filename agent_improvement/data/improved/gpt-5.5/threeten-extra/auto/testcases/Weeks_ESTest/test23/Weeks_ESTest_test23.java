package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.DateTimeException;
import java.time.Duration;
import java.time.temporal.TemporalAmount;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Weeks_ESTest_test23 extends Weeks_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test23() throws Throwable {
        Weeks zeroWeeks = Weeks.ZERO;
        Duration negativeDurationThatIsNotWholeWeeks = Duration.ofDays((-705L));

        try {
            zeroWeeks.plus((TemporalAmount) negativeDurationThatIsNotWholeWeeks);
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException exception) {
            verifyException("org.threeten.extra.Weeks", exception);
        }
    }
}
