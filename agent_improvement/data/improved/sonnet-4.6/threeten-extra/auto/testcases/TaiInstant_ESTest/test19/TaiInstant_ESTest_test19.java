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

    @Test(timeout = 4000)
    public void test_toInstant_doesNotMutateOriginalTaiInstant() throws Throwable {
        TaiInstant taiInstant = TaiInstant.ofTaiSeconds(20, 20);

        // toInstant() converts to UTC-SLS; the result is unused here because
        // the purpose is to confirm that the immutable TaiInstant is unaffected.
        taiInstant.toInstant();

        assertEquals(20L, taiInstant.getTaiSeconds());
        assertEquals(20, taiInstant.getNano());
    }
}
