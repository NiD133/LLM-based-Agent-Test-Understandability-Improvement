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
public class TaiInstant_ESTest_test22 extends TaiInstant_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test22() throws Throwable {
        long taiSeconds = 634L;
        long nanoAdjustment = 634L;

        TaiInstant taiInstant = TaiInstant.ofTaiSeconds(taiSeconds, nanoAdjustment);
        taiInstant.hashCode();

        assertEquals("The nanosecond adjustment is already within one second.", 634, taiInstant.getNano());
        assertEquals("The TAI second value should be stored unchanged.", 634L, taiInstant.getTaiSeconds());
    }
}
