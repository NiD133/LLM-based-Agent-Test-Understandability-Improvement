package org.threeten.extra.scale;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.StringWriter;
import java.time.Duration;
import java.time.Instant;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class UtcInstant_ESTest_test02 extends UtcInstant_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test02() throws Throwable {
        // Exercise formatting near the upper end of the supported Instant range.
        Instant upperBoundaryInstant = MockInstant.ofEpochSecond(86400000000000L);
        Instant threeNanosBeforeBoundary = MockInstant.minusNanos(upperBoundaryInstant, 3L);

        UtcInstant utcInstant = UtcInstant.of(threeNanosBeforeBoundary);
        String formattedInstant = utcInstant.toString();

        assertNotNull(formattedInstant);
        assertEquals("+2739877-01-02T23:59:59.999999997Z", formattedInstant);
    }
}
