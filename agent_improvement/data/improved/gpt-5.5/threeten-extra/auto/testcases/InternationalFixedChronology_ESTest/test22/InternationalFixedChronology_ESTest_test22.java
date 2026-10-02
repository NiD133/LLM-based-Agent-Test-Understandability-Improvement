package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.chrono.ChronoZonedDateTime;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockClock;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class InternationalFixedChronology_ESTest_test22 extends InternationalFixedChronology_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test22() throws Throwable {
        InternationalFixedChronology chronology = new InternationalFixedChronology();
        Instant epochInstant = MockInstant.ofEpochSecond(3L, 3L);
        ZoneOffset utcZone = ZoneOffset.UTC;

        ChronoZonedDateTime<InternationalFixedDate> zonedDateTime =
                chronology.zonedDateTime(epochInstant, (ZoneId) utcZone);

        assertNotNull(zonedDateTime);
    }
}
