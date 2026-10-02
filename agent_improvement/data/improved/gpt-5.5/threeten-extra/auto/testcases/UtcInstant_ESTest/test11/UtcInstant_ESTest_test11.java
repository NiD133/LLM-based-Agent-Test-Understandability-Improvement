package org.threeten.extra.scale;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.StringWriter;
import java.time.Duration;
import java.time.Instant;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class UtcInstant_ESTest_test11 extends UtcInstant_ESTest_scaffolding {

    private static final long TAI_SECONDS = -24L;
    private static final long TAI_NANO_ADJUSTMENT = -24L;
    private static final long REPLACEMENT_MODIFIED_JULIAN_DAY = -24L;
    private static final long EXPECTED_ORIGINAL_NANO_OF_DAY = 86365999999976L;

    @Test(timeout = 4000)
    public void test11() throws Throwable {
        TaiInstant taiInstant = TaiInstant.ofTaiSeconds(TAI_SECONDS, TAI_NANO_ADJUSTMENT);
        UtcInstant originalUtcInstant = taiInstant.toUtcInstant();
        UtcInstant sameTimeOfDayOnReplacementDay =
                originalUtcInstant.withModifiedJulianDay(REPLACEMENT_MODIFIED_JULIAN_DAY);

        boolean originalIsAfterReplacementDay = originalUtcInstant.isAfter(sameTimeOfDayOnReplacementDay);

        assertEquals(REPLACEMENT_MODIFIED_JULIAN_DAY, sameTimeOfDayOnReplacementDay.getModifiedJulianDay());
        assertTrue(originalIsAfterReplacementDay);
        assertEquals(EXPECTED_ORIGINAL_NANO_OF_DAY, originalUtcInstant.getNanoOfDay());
    }
}
