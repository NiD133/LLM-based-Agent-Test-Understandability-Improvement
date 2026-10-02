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
public class UtcInstant_ESTest_test04 extends UtcInstant_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        Instant instant0 = MockInstant.now();
        UtcInstant utcInstant0 = UtcInstant.of(instant0);
        UtcInstant utcInstant1 = utcInstant0.withNanoOfDay(1136L);
        boolean boolean0 = utcInstant1.equals(utcInstant0);
        assertEquals(56702L, utcInstant0.getModifiedJulianDay());
        assertFalse(boolean0);
        assertEquals(56702L, utcInstant1.getModifiedJulianDay());
        assertFalse(utcInstant0.equals((Object) utcInstant1));
    }
}
