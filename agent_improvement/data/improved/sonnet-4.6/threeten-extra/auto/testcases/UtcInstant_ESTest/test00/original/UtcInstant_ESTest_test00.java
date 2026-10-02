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

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        TaiInstant taiInstant0 = TaiInstant.ofTaiSeconds((-745L), (-1000L));
        UtcInstant utcInstant0 = UtcInstant.of(taiInstant0);
        String string0 = utcInstant0.toString();
        assertEquals("1957-12-31T23:47:24.999999Z", string0);
        assertNotNull(string0);
    }
}
