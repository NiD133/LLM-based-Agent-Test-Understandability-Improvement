package org.threeten.extra.scale;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class UtcInstant_ESTest_test00 extends UtcInstant_ESTest_scaffolding {

    /**
     * Converting a TAI instant to a UTC instant should produce the equivalent
     * point on the UTC time-scale, and its ISO-8601 string should reflect that
     * the nanosecond fraction (-1000 ns relative to the second) rolls the value
     * back to one microsecond before the whole second.
     */
    @Test(timeout = 4000)
    public void convertTaiInstantToUtcAndFormatAsString() throws Throwable {
        long taiSeconds = -745L;
        long taiNanoAdjustment = -1000L;
        TaiInstant taiInstant = TaiInstant.ofTaiSeconds(taiSeconds, taiNanoAdjustment);

        UtcInstant utcInstant = UtcInstant.of(taiInstant);
        String formatted = utcInstant.toString();

        assertNotNull(formatted);
        assertEquals("1957-12-31T23:47:24.999999Z", formatted);
    }
}
