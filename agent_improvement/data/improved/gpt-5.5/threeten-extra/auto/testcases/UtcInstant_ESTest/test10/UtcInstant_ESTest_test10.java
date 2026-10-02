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
public class UtcInstant_ESTest_test10 extends UtcInstant_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test10() throws Throwable {
        Instant threeSecondsAfterEpoch = MockInstant.ofEpochSecond(3L);
        UtcInstant utcInstant = UtcInstant.of(threeSecondsAfterEpoch);

        boolean instantIsBeforeItself = utcInstant.isBefore(utcInstant);

        assertEquals(3000000000L, utcInstant.getNanoOfDay());
        assertEquals(40587L, utcInstant.getModifiedJulianDay());
        assertFalse(instantIsBeforeItself);
    }
}
