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
public class UtcInstant_ESTest_test14 extends UtcInstant_ESTest_scaffolding {

    private static final String FAR_FUTURE_UTC_INSTANT = "+2739877-01-02T23:59:59.999999997Z";
    private static final long EXPECTED_NANO_OF_DAY = 86_399_999_999_997L;
    private static final long EXPECTED_MODIFIED_JULIAN_DAY = 1_000_040_586L;

    @Test(timeout = 4000)
    public void test14() throws Throwable {
        UtcInstant parsedInstant = UtcInstant.parse(FAR_FUTURE_UTC_INSTANT);

        assertEquals(EXPECTED_NANO_OF_DAY, parsedInstant.getNanoOfDay());
        assertEquals(EXPECTED_MODIFIED_JULIAN_DAY, parsedInstant.getModifiedJulianDay());
    }
}
