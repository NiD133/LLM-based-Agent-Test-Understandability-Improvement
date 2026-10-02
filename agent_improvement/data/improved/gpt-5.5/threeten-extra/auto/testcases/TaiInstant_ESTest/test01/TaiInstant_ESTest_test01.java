package org.threeten.extra.scale;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.CharBuffer;
import java.time.Duration;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TaiInstant_ESTest_test01 extends TaiInstant_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        TaiInstant firstInstant = TaiInstant.ofTaiSeconds((-3113L), (-1192L));
        TaiInstant instantWithNormalizedNegativeNanos = TaiInstant.ofTaiSeconds((-1192L), (-3113L));

        boolean sameInstant = firstInstant.equals(instantWithNormalizedNegativeNanos);

        assertFalse(sameInstant);
        assertEquals(999996887, instantWithNormalizedNegativeNanos.getNano());
        assertEquals((-1193L), instantWithNormalizedNegativeNanos.getTaiSeconds());
    }
}
