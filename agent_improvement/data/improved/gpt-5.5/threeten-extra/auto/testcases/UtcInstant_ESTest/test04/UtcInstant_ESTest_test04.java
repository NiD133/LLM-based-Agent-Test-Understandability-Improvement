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
public class UtcInstant_ESTest_test04 extends UtcInstant_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        Instant currentInstant = MockInstant.now();
        UtcInstant originalUtcInstant = UtcInstant.of(currentInstant);
        UtcInstant utcInstantAtNanoOfDay = originalUtcInstant.withNanoOfDay(1136L);

        boolean equalsOriginalResult = utcInstantAtNanoOfDay.equals(originalUtcInstant);

        assertEquals(56702L, originalUtcInstant.getModifiedJulianDay());
        assertFalse(equalsOriginalResult);
        assertEquals(56702L, utcInstantAtNanoOfDay.getModifiedJulianDay());
        assertFalse(originalUtcInstant.equals((Object) utcInstantAtNanoOfDay));
    }
}
