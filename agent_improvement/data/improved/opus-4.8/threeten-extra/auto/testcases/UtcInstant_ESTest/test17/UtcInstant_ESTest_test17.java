package org.threeten.extra.scale;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.time.Instant;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class UtcInstant_ESTest_test17 extends UtcInstant_ESTest_scaffolding {

    /**
     * Verifies that converting a (mocked, fixed) Instant into a UtcInstant
     * splits the instant into the expected Modified Julian Day and nano-of-day,
     * and that hashCode() can be invoked without affecting those fields.
     */
    @Test(timeout = 4000)
    public void convertingMockedInstantYieldsExpectedDayAndNanoOfDay() throws Throwable {
        Instant fixedInstant = MockInstant.now();

        UtcInstant utcInstant = UtcInstant.of(fixedInstant);
        utcInstant.hashCode();

        assertEquals(73281320000000L, utcInstant.getNanoOfDay());
        assertEquals(56702L, utcInstant.getModifiedJulianDay());
    }
}
