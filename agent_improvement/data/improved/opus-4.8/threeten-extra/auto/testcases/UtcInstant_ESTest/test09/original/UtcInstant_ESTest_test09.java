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
public class UtcInstant_ESTest_test09 extends UtcInstant_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test09() throws Throwable {
        UtcInstant utcInstant0 = UtcInstant.ofModifiedJulianDay(13L, 13L);
        UtcInstant utcInstant1 = UtcInstant.ofModifiedJulianDay(13L, 86398161000000L);
        boolean boolean0 = utcInstant0.isBefore(utcInstant1);
        assertEquals(13L, utcInstant1.getModifiedJulianDay());
        assertTrue(boolean0);
    }
}
