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
public class TaiInstant_ESTest_test00 extends TaiInstant_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        TaiInstant taiInstant0 = TaiInstant.ofTaiSeconds((-3113L), (-3113L));
        TaiInstant taiInstant1 = TaiInstant.ofTaiSeconds((-3113L), (-3113L));
        boolean boolean0 = taiInstant0.equals(taiInstant1);
        assertEquals(999996887, taiInstant1.getNano());
        assertTrue(boolean0);
        assertEquals((-3114L), taiInstant1.getTaiSeconds());
    }
}
