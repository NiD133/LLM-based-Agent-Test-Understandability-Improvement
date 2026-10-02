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
public class UtcInstant_ESTest_test06 extends UtcInstant_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test06() throws Throwable {
        long modifiedJulianDay = 13L;
        long nanoOfDay = 13L;

        UtcInstant originalInstant = UtcInstant.ofModifiedJulianDay(modifiedJulianDay, nanoOfDay);
        UtcInstant instantWithSameModifiedJulianDay = originalInstant.withModifiedJulianDay(modifiedJulianDay);

        boolean isEqualToOriginal = instantWithSameModifiedJulianDay.equals(originalInstant);

        assertEquals(modifiedJulianDay, instantWithSameModifiedJulianDay.getModifiedJulianDay());
        assertTrue(isEqualToOriginal);
    }
}
