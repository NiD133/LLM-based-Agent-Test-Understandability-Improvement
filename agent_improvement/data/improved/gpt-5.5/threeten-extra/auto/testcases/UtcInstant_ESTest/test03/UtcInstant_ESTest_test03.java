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
public class UtcInstant_ESTest_test03 extends UtcInstant_ESTest_scaffolding {

    private static final long THREE_SECONDS_AFTER_EPOCH = 3L;
    private static final String EXPECTED_UTC_TEXT = "1970-01-01T00:00:03Z";

    @Test(timeout = 4000)
    public void test03() throws Throwable {
        Instant threeSecondsAfterEpoch = MockInstant.ofEpochSecond(THREE_SECONDS_AFTER_EPOCH);
        UtcInstant utcInstant = UtcInstant.of(threeSecondsAfterEpoch);

        utcInstant.toString();
        String formattedInstant = utcInstant.toString();

        assertNotNull(formattedInstant);
        assertEquals(EXPECTED_UTC_TEXT, formattedInstant);
    }
}
