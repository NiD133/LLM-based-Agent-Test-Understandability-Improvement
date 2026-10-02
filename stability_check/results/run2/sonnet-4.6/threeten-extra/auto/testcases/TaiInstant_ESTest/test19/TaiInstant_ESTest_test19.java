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
public class TaiInstant_ESTest_test19 extends TaiInstant_ESTest_scaffolding {

    // Verifies that toInstant() does not mutate the TaiInstant's seconds or nanos fields.
    @Test(timeout = 4000)
    public void test_toInstant_doesNotMutateOriginalFields() throws Throwable {
        long taiSeconds = 20L;
        int nanoAdjustment = 20;
        TaiInstant taiInstant = TaiInstant.ofTaiSeconds(taiSeconds, nanoAdjustment);

        taiInstant.toInstant();

        assertEquals(taiSeconds, taiInstant.getTaiSeconds());
        assertEquals(nanoAdjustment, taiInstant.getNano());
    }
}
