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
public class TaiInstant_ESTest_test02 extends TaiInstant_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test02() throws Throwable {
        TaiInstant taiInstant0 = TaiInstant.ofTaiSeconds((-1L), (-1L));
        Object object0 = new Object();
        boolean boolean0 = taiInstant0.equals(object0);
        assertEquals((-2L), taiInstant0.getTaiSeconds());
        assertFalse(boolean0);
        assertEquals(999999999, taiInstant0.getNano());
    }
}
