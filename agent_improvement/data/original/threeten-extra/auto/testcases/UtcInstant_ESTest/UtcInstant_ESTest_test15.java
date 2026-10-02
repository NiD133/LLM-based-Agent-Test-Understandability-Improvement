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
public class UtcInstant_ESTest_test15 extends UtcInstant_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test15() throws Throwable {
        Instant instant0 = MockInstant.now();
        UtcInstant utcInstant0 = UtcInstant.of(instant0);
        Instant instant1 = utcInstant0.toInstant();
        assertTrue(instant1.equals((Object) instant0));
        assertEquals(56702L, utcInstant0.getModifiedJulianDay());
    }
}
