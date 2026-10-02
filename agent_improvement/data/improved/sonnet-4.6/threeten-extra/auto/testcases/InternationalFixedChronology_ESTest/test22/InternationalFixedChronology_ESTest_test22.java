package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.chrono.ChronoZonedDateTime;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class InternationalFixedChronology_ESTest_test22 extends InternationalFixedChronology_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_zonedDateTime_fromInstantWithUtcZone_returnsNonNull() throws Throwable {
        // 3 seconds and 3 nanoseconds after the Unix epoch
        Instant instant = MockInstant.ofEpochSecond(3L, 3L);
        ZoneId utcZone = ZoneOffset.UTC;

        ChronoZonedDateTime<InternationalFixedDate> result =
                InternationalFixedChronology.INSTANCE.zonedDateTime(instant, utcZone);

        assertNotNull(result);
    }
}
