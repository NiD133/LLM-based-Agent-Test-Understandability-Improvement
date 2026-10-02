package org.threeten.extra.scale;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.Instant;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class UtcInstant_ESTest_test15 extends UtcInstant_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test15() throws Throwable {
        long expectedModifiedJulianDay = 56702L;

        Instant mockedCurrentInstant = MockInstant.now();
        UtcInstant utcInstant = UtcInstant.of(mockedCurrentInstant);

        Instant roundTrippedInstant = utcInstant.toInstant();

        assertTrue(roundTrippedInstant.equals((Object) mockedCurrentInstant));
        assertEquals(expectedModifiedJulianDay, utcInstant.getModifiedJulianDay());
    }
}
