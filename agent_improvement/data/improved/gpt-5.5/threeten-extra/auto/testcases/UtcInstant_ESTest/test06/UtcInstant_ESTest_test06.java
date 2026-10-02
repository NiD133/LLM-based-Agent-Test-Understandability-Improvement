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
public class UtcInstant_ESTest_test06 extends UtcInstant_ESTest_scaffolding {

    private static final long MODIFIED_JULIAN_DAY = 13L;
    private static final long NANO_OF_DAY = 13L;

    @Test(timeout = 4000)
    public void test06() throws Throwable {
        UtcInstant originalInstant = UtcInstant.ofModifiedJulianDay(MODIFIED_JULIAN_DAY, NANO_OF_DAY);

        UtcInstant sameDayInstant = originalInstant.withModifiedJulianDay(MODIFIED_JULIAN_DAY);
        boolean hasSameDateAndTime = sameDayInstant.equals(originalInstant);

        assertEquals(MODIFIED_JULIAN_DAY, sameDayInstant.getModifiedJulianDay());
        assertTrue(hasSameDateAndTime);
    }
}
