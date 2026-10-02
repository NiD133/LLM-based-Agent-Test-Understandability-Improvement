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

    // Expected TAI values when converting the mocked "now" Instant to a TaiInstant.
    // MockInstant.now() returns a fixed deterministic instant under EvoSuite's mock JVM.
    private static final int EXPECTED_NANO_OF_SECOND = 320000000;
    private static final long EXPECTED_TAI_SECONDS   = 1771100516L;

    @Test(timeout = 4000)
    public void test_ofInstant_returnsCorrectTaiSecondsAndNano() throws Throwable {
        Instant mockInstant = MockInstant.now();
        TaiInstant taiInstant = TaiInstant.of(mockInstant);

        assertEquals(EXPECTED_NANO_OF_SECOND, taiInstant.getNano());
        assertEquals(EXPECTED_TAI_SECONDS,    taiInstant.getTaiSeconds());
    }
}
