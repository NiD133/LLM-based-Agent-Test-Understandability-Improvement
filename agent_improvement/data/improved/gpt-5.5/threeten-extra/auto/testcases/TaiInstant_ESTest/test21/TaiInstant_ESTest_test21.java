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
public class TaiInstant_ESTest_test21 extends TaiInstant_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test21() throws Throwable {
        Instant mockedCurrentInstant = MockInstant.now();

        TaiInstant taiInstant = TaiInstant.of(mockedCurrentInstant);

        assertEquals(320000000, taiInstant.getNano());
        assertEquals(1771100516L, taiInstant.getTaiSeconds());
    }
}
