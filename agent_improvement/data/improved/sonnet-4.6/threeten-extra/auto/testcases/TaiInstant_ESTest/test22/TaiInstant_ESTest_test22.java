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

    // nanoAdjustment fits within one second, so seconds and nanos are stored as-is
    private static final long TAI_SECONDS = 634L;
    private static final long NANO_ADJUSTMENT = 634L;

    @Test(timeout = 4000)
    public void test_hashCode_doesNotThrow_andGettersReturnConstructedValues() throws Throwable {
        TaiInstant taiInstant = TaiInstant.ofTaiSeconds(TAI_SECONDS, NANO_ADJUSTMENT);

        taiInstant.hashCode();

        assertEquals(634, taiInstant.getNano());
        assertEquals(634L, taiInstant.getTaiSeconds());
    }
}
