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
public class UtcInstant_ESTest_test18 extends UtcInstant_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test18() throws Throwable {
        TaiInstant taiInstant = TaiInstant.ofTaiSeconds((-745L), (-1000L));
        UtcInstant utcInstant = UtcInstant.of(taiInstant);

        Duration negativeThousandMinutes = Duration.ofMinutes((-1000L));
        UtcInstant shiftedUtcInstant = utcInstant.plus(negativeThousandMinutes);

        assertEquals(36203L, utcInstant.getModifiedJulianDay());
        assertEquals(36203L, shiftedUtcInstant.getModifiedJulianDay());
    }
}
