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
public class TaiInstant_ESTest_test07 extends TaiInstant_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test07() throws Throwable {
        TaiInstant taiInstant0 = TaiInstant.ofTaiSeconds((-3113L), (-1194L));
        TaiInstant taiInstant1 = taiInstant0.withNano(0);
        boolean boolean0 = taiInstant0.isAfter(taiInstant1);
        assertEquals((-3114L), taiInstant1.getTaiSeconds());
        assertEquals(0, taiInstant1.getNano());
        assertEquals((-3114L), taiInstant0.getTaiSeconds());
        assertTrue(boolean0);
        assertEquals(999998806, taiInstant0.getNano());
    }
}
