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
public class UtcInstant_ESTest_test08 extends UtcInstant_ESTest_scaffolding {

    // Unix epoch is MJD 40587; 3 seconds in corresponds to nanoOfDay = 3 * 1_000_000_000
    private static final long MJD_OF_UNIX_EPOCH = 40587L;
    private static final long NANOS_PER_SECOND = 1_000_000_000L;

    @Test(timeout = 4000)
    public void test08_equalsReturnsFalseForNonUtcInstantObject() throws Throwable {
        Instant threeSecondsAfterUnixEpoch = MockInstant.ofEpochSecond(3L);
        UtcInstant utcInstant = UtcInstant.of(threeSecondsAfterUnixEpoch);

        // Use a StringBuffer (obtained via StringWriter) as an incompatible type to test equals()
        StringWriter stringWriter = new StringWriter();
        StringBuffer stringBuffer = stringWriter.getBuffer();

        boolean equalsResult = utcInstant.equals(stringBuffer);

        assertFalse("UtcInstant.equals() should return false when compared to a non-UtcInstant object", equalsResult);
        assertEquals("MJD should match the Unix epoch day", MJD_OF_UNIX_EPOCH, utcInstant.getModifiedJulianDay());
        assertEquals("nanoOfDay should be 3 seconds expressed in nanoseconds", 3L * NANOS_PER_SECOND, utcInstant.getNanoOfDay());
    }
}
