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
public class UtcInstant_ESTest_test02 extends UtcInstant_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test02() throws Throwable {
        Instant instant0 = MockInstant.ofEpochSecond(86400000000000L);
        Instant instant1 = MockInstant.minusNanos(instant0, 3L);
        UtcInstant utcInstant0 = UtcInstant.of(instant1);
        String string0 = utcInstant0.toString();
        assertNotNull(string0);
        assertEquals("+2739877-01-02T23:59:59.999999997Z", string0);
    }
}
