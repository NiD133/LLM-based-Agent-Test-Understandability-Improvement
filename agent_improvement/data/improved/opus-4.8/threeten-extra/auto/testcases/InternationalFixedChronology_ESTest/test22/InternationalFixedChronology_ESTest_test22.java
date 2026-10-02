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

    /**
     * Verifies that {@link InternationalFixedChronology#zonedDateTime(Instant, ZoneId)}
     * builds a non-null zoned date-time from a given instant and UTC zone.
     */
    @Test(timeout = 4000)
    public void zonedDateTimeFromInstantAndUtcZoneIsNotNull() throws Throwable {
        InternationalFixedChronology chronology = new InternationalFixedChronology();
        Instant instant = MockInstant.ofEpochSecond(3L, 3L);
        ZoneId utcZone = ZoneOffset.UTC;

        ChronoZonedDateTime<InternationalFixedDate> zonedDateTime =
                chronology.zonedDateTime(instant, utcZone);

        assertNotNull(zonedDateTime);
    }
}
