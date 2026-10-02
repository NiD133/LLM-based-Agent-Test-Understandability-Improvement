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
public class TaiInstant_ESTest_test11 extends TaiInstant_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test11() throws Throwable {
        TaiInstant taiInstant0 = TaiInstant.ofTaiSeconds((-10), (-10));
        Duration duration0 = Duration.ZERO;
        TaiInstant taiInstant1 = taiInstant0.plus(duration0);
        assertSame(taiInstant1, taiInstant0);
        assertEquals((-11L), taiInstant1.getTaiSeconds());
        assertEquals(999999990, taiInstant1.getNano());
    }
}
