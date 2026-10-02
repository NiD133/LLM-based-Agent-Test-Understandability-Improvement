package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.ZoneId;
import java.time.ZoneOffset;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DayOfYear_ESTest_test17 extends DayOfYear_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test17() throws Throwable {
        // ZoneOffset.MAX is UTC+18:00; querying "now" in that extreme zone can yield a different calendar day
        ZoneOffset maxZoneOffset = ZoneOffset.MAX;
        DayOfYear dayInMaxOffset = DayOfYear.now((ZoneId) maxZoneOffset);
        DayOfYear dayInDefaultZone = DayOfYear.now();

        // The two instances represent different days, so neither should equal the other
        boolean defaultZoneEqualsMaxOffset = dayInDefaultZone.equals(dayInMaxOffset);
        assertFalse(dayInMaxOffset.equals((Object) dayInDefaultZone));
        assertFalse(defaultZoneEqualsMaxOffset);

        // The mocked clock places the default-zone date at day 45 of the year
        assertEquals(45, dayInDefaultZone.getValue());
    }
}
