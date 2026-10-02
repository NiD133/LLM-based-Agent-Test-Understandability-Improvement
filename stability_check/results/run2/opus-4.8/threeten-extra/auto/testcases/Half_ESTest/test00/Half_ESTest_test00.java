package org.threeten.extra;

import static org.junit.Assert.fail;
import static org.evosuite.runtime.EvoAssertions.verifyException;

import java.time.DateTimeException;
import java.time.DayOfWeek;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Half_ESTest_test00 extends Half_ESTest_scaffolding {

    /**
     * A {@link DayOfWeek} carries no half-of-year information, so {@code Half.from}
     * cannot derive a {@link Half} from it and must fail with a {@link DateTimeException}.
     */
    @Test(timeout = 4000)
    public void from_dayOfWeek_throwsDateTimeException() throws Throwable {
        DayOfWeek unsupportedTemporal = DayOfWeek.WEDNESDAY;

        try {
            Half.from(unsupportedTemporal);
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException e) {
            // Unable to obtain Half from TemporalAccessor: WEDNESDAY of type java.time.DayOfWeek
            verifyException("org.threeten.extra.Half", e);
        }
    }
}
