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
public class UtcInstant_ESTest_test15 extends UtcInstant_ESTest_scaffolding {

    private static final long EXPECTED_MODIFIED_JULIAN_DAY_FOR_MOCKED_NOW = 56702L;

    @Test(timeout = 4000)
    public void test15() throws Throwable {
        Instant mockedCurrentInstant = MockInstant.now();

        UtcInstant utcInstant = UtcInstant.of(mockedCurrentInstant);
        Instant convertedBackToInstant = utcInstant.toInstant();

        assertTrue(convertedBackToInstant.equals((Object) mockedCurrentInstant));
        assertEquals(EXPECTED_MODIFIED_JULIAN_DAY_FOR_MOCKED_NOW, utcInstant.getModifiedJulianDay());
    }
}
