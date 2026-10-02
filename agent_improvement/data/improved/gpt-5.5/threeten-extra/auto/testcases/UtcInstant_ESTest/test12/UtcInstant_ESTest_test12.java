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
public class UtcInstant_ESTest_test12 extends UtcInstant_ESTest_scaffolding {

    private static final long EPOCH_SECOND = 3L;
    private static final long EXPECTED_NANO_OF_DAY = 3000000000L;
    private static final long EXPECTED_MODIFIED_JULIAN_DAY = 40587L;

    @Test(timeout = 4000)
    public void test12() throws Throwable {
        Instant javaInstant = MockInstant.ofEpochSecond(EPOCH_SECOND);
        UtcInstant utcInstant = UtcInstant.of(javaInstant);

        boolean isAfterItself = utcInstant.isAfter(utcInstant);

        assertEquals(EXPECTED_NANO_OF_DAY, utcInstant.getNanoOfDay());
        assertFalse(isAfterItself);
        assertEquals(EXPECTED_MODIFIED_JULIAN_DAY, utcInstant.getModifiedJulianDay());
    }
}
