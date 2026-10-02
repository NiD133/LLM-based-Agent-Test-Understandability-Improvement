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
public class UtcInstant_ESTest_test14 extends UtcInstant_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test14() throws Throwable {
        UtcInstant utcInstant0 = UtcInstant.parse("+2739877-01-02T23:59:59.999999997Z");
        assertEquals(86399999999997L, utcInstant0.getNanoOfDay());
        assertEquals(1000040586L, utcInstant0.getModifiedJulianDay());
    }
}
