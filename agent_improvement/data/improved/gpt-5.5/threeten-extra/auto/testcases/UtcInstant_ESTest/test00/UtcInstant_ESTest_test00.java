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
public class UtcInstant_ESTest_test00 extends UtcInstant_ESTest_scaffolding {

    private static final long TAI_SECONDS_BEFORE_UTC_EPOCH = -745L;
    private static final long NEGATIVE_NANO_ADJUSTMENT = -1000L;
    private static final String EXPECTED_UTC_TEXT = "1957-12-31T23:47:24.999999Z";

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        TaiInstant taiInstant = TaiInstant.ofTaiSeconds(
                TAI_SECONDS_BEFORE_UTC_EPOCH,
                NEGATIVE_NANO_ADJUSTMENT);

        UtcInstant utcInstant = UtcInstant.of(taiInstant);
        String utcText = utcInstant.toString();

        assertEquals(EXPECTED_UTC_TEXT, utcText);
        assertNotNull(utcText);
    }
}
