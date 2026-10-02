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

    @Test(timeout = 4000)
    public void test03() throws Throwable {
        Instant instant0 = MockInstant.ofEpochSecond(3L);
        UtcInstant utcInstant0 = UtcInstant.of(instant0);
        utcInstant0.toString();
        String string0 = utcInstant0.toString();
        assertNotNull(string0);
        assertEquals("1970-01-01T00:00:03Z", string0);
    }
}
