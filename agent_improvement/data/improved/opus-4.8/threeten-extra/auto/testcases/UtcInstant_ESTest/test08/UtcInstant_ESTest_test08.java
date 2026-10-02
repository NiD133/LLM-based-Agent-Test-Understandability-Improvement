package org.threeten.extra.scale;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.StringWriter;
import java.time.Instant;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class UtcInstant_ESTest_test08 extends UtcInstant_ESTest_scaffolding {

    /**
     * A UtcInstant should never be considered equal to an object of an
     * unrelated type, and converting an Instant should populate the
     * expected Modified Julian Day and nano-of-day fields.
     */
    @Test(timeout = 4000)
    public void equalsAgainstUnrelatedTypeIsFalseAndFieldsAreSet() throws Throwable {
        // 1970-01-01T00:00:03Z, i.e. 3 seconds after the Unix epoch.
        Instant threeSecondsAfterEpoch = MockInstant.ofEpochSecond(3L);
        UtcInstant utcInstant = UtcInstant.of(threeSecondsAfterEpoch);

        // An arbitrary object whose type is unrelated to UtcInstant.
        Object unrelatedObject = new StringWriter().getBuffer();

        assertFalse("UtcInstant must not equal an unrelated type",
                utcInstant.equals(unrelatedObject));

        // The Unix epoch falls on Modified Julian Day 40587.
        assertEquals(40587L, utcInstant.getModifiedJulianDay());
        // 3 seconds into the day, expressed in nanoseconds.
        assertEquals(3_000_000_000L, utcInstant.getNanoOfDay());
    }
}
