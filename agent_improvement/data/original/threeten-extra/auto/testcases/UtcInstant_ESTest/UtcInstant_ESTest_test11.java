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
public class UtcInstant_ESTest_test11 extends UtcInstant_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test11() throws Throwable {
        TaiInstant taiInstant0 = TaiInstant.ofTaiSeconds((-24L), (-24L));
        UtcInstant utcInstant0 = taiInstant0.toUtcInstant();
        UtcInstant utcInstant1 = utcInstant0.withModifiedJulianDay((-24L));
        boolean boolean0 = utcInstant0.isAfter(utcInstant1);
        assertEquals((-24L), utcInstant1.getModifiedJulianDay());
        assertTrue(boolean0);
        assertEquals(86365999999976L, utcInstant0.getNanoOfDay());
    }
}
