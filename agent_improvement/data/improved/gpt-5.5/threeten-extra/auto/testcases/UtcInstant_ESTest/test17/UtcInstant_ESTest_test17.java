package org.threeten.extra.scale;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.Instant;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class UtcInstant_ESTest_test17 extends UtcInstant_ESTest_scaffolding {

    private static final long EXPECTED_NANO_OF_DAY = 73281320000000L;
    private static final long EXPECTED_MODIFIED_JULIAN_DAY = 56702L;

    @Test(timeout = 4000)
    public void test17() throws Throwable {
        Instant currentMockInstant = MockInstant.now();
        UtcInstant utcInstant = UtcInstant.of(currentMockInstant);

        utcInstant.hashCode();

        assertEquals(EXPECTED_NANO_OF_DAY, utcInstant.getNanoOfDay());
        assertEquals(EXPECTED_MODIFIED_JULIAN_DAY, utcInstant.getModifiedJulianDay());
    }
}
