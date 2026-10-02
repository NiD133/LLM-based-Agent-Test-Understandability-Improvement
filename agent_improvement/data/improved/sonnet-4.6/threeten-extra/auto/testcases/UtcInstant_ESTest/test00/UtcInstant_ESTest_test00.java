package org.threeten.extra.scale;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class UtcInstant_ESTest_test00 extends UtcInstant_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        // A TAI instant from late 1957 (pre-UTC-epoch), offset by -1000 nanoseconds
        TaiInstant taiInstant = TaiInstant.ofTaiSeconds((-745L), (-1000L));
        UtcInstant utcInstant = UtcInstant.of(taiInstant);

        String isoString = utcInstant.toString();

        assertEquals("1957-12-31T23:47:24.999999Z", isoString);
        assertNotNull(isoString);
    }
}
