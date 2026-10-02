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
public class UtcInstant_ESTest_test05 extends UtcInstant_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test05() throws Throwable {
        Instant instant0 = MockInstant.now();
        TaiInstant taiInstant0 = TaiInstant.ofTaiSeconds(36791000000652L, 36791000000652L);
        UtcInstant utcInstant0 = taiInstant0.toUtcInstant();
        UtcInstant utcInstant1 = UtcInstant.of(instant0);
        boolean boolean0 = utcInstant1.equals(utcInstant0);
        assertEquals(56702L, utcInstant1.getModifiedJulianDay());
        assertFalse(boolean0);
        assertEquals(73281320000000L, utcInstant1.getNanoOfDay());
    }
}
