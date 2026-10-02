package org.apache.commons.lang3.time;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DurationUtils_ESTest_test11 extends DurationUtils_ESTest_scaffolding {

    /**
     * Verifies that {@link DurationUtils#get(String, java.time.temporal.TemporalUnit, long)}
     * builds a non-null Duration when the requested system property is absent.
     *
     * <p>Because the property key does not exist, {@code get} falls back to the supplied
     * default amount (-210) and interprets it using the given unit.</p>
     */
    @Test(timeout = 4000)
    public void get_withUnknownSystemProperty_usesDefaultAmount() throws Throwable {
        // MILLISECONDS maps to the ChronoUnit.MILLIS temporal unit.
        ChronoUnit milliUnit = DurationUtils.toChronoUnit(TimeUnit.MILLISECONDS);

        // The key is not a defined system property, so the default of -210 millis is used.
        String missingPropertyKey = "_\"/5Q'";
        long defaultMillis = -210L;
        Duration duration = DurationUtils.get(missingPropertyKey, milliUnit, defaultMillis);

        assertNotNull(duration);
    }
}
