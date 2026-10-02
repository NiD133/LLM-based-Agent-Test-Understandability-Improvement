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
        TaiInstant taiInstant0 = TaiInstant.ofTaiSeconds((-3113L), (-1192L));
        TaiInstant taiInstant1 = TaiInstant.ofTaiSeconds((-1192L), (-3113L));
        boolean boolean0 = taiInstant0.equals(taiInstant1);
        assertFalse(boolean0);
        assertEquals(999996887, taiInstant1.getNano());
        assertEquals((-1193L), taiInstant1.getTaiSeconds());
    }
}
