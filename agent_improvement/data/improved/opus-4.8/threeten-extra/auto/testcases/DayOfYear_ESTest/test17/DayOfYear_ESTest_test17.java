package org.threeten.extra;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.time.ZoneId;
import java.time.ZoneOffset;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DayOfYear_ESTest_test17 extends DayOfYear_ESTest_scaffolding {

    /**
     * Because the mocked system clock is fixed, {@code DayOfYear.now()} in the
     * default time-zone resolves to day-of-year 45, while {@code DayOfYear.now}
     * in the maximum ZoneOffset falls on a different day. The two instances are
     * therefore not equal, regardless of which side of the comparison they are on.
     */
    @Test(timeout = 4000)
    public void now_inDefaultZone_differsFromNow_inMaxOffset() throws Throwable {
        DayOfYear dayInMaxOffset = DayOfYear.now((ZoneId) ZoneOffset.MAX);
        DayOfYear dayInDefaultZone = DayOfYear.now();

        assertEquals(45, dayInDefaultZone.getValue());
        assertFalse(dayInDefaultZone.equals(dayInMaxOffset));
        assertFalse(dayInMaxOffset.equals((Object) dayInDefaultZone));
    }
}
