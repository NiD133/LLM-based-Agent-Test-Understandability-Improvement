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
public class TaiInstant_ESTest_test12 extends TaiInstant_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test12() throws Throwable {
        Duration duration0 = Duration.ofMillis((-3113L));
        UtcInstant utcInstant0 = UtcInstant.ofModifiedJulianDay((-1194L), 0);
        UtcInstant utcInstant1 = utcInstant0.plus(duration0);
        assertEquals(86396887000000L, utcInstant1.getNanoOfDay());
    }
}
