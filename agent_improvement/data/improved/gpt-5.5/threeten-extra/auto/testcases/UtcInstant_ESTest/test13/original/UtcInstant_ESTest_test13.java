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
public class UtcInstant_ESTest_test13 extends UtcInstant_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test13() throws Throwable {
        Instant instant0 = MockInstant.ofEpochSecond(3L);
        UtcInstant utcInstant0 = UtcInstant.of(instant0);
        boolean boolean0 = utcInstant0.isLeapSecond();
        assertEquals(3000000000L, utcInstant0.getNanoOfDay());
        assertEquals(40587L, utcInstant0.getModifiedJulianDay());
        assertFalse(boolean0);
    }
}
