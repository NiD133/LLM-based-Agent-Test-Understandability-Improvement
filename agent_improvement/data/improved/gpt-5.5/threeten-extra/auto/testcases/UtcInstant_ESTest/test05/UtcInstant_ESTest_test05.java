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
public class UtcInstant_ESTest_test05 extends UtcInstant_ESTest_scaffolding {

    private static final long TAI_SECONDS = 36791000000652L;
    private static final long TAI_NANOS = 36791000000652L;
    private static final long EXPECTED_MODIFIED_JULIAN_DAY = 56702L;
    private static final long EXPECTED_NANO_OF_DAY = 73281320000000L;

    @Test(timeout = 4000)
    public void test05() throws Throwable {
        Instant mockedCurrentInstant = MockInstant.now();
        TaiInstant distantTaiInstant = TaiInstant.ofTaiSeconds(TAI_SECONDS, TAI_NANOS);
        UtcInstant utcFromTaiInstant = distantTaiInstant.toUtcInstant();
        UtcInstant utcFromMockedInstant = UtcInstant.of(mockedCurrentInstant);

        boolean sameUtcInstant = utcFromMockedInstant.equals(utcFromTaiInstant);

        assertEquals(EXPECTED_MODIFIED_JULIAN_DAY, utcFromMockedInstant.getModifiedJulianDay());
        assertFalse(sameUtcInstant);
        assertEquals(EXPECTED_NANO_OF_DAY, utcFromMockedInstant.getNanoOfDay());
    }
}
