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
     * Verifies that {@code DurationUtils.get()} returns a non-null Duration when
     * the requested system property does not exist, falling back to a negative default value.
     *
     * Steps:
     *   1. Convert {@code TimeUnit.MILLISECONDS} to its {@code ChronoUnit} equivalent (MILLIS).
     *   2. Call {@code get()} with a property key that is guaranteed not to exist as a JVM
     *      system property, so the method uses the supplied default value (-210 ms).
     *   3. Assert that the returned {@code Duration} is not null.
     */
    @Test(timeout = 4000)
    public void test11() throws Throwable {
        // Convert the Java-concurrent TimeUnit to its java.time ChronoUnit counterpart
        ChronoUnit millisChronoUnit = DurationUtils.toChronoUnit(TimeUnit.MILLISECONDS);

        // Use a key that will never match a real system property, forcing the default value
        String nonExistentPropertyKey = "_\"/5Q'";
        long defaultMillis = -210L;

        // get() looks up the system property; on a miss it creates a Duration from the default
        Duration duration = DurationUtils.get(nonExistentPropertyKey, millisChronoUnit, defaultMillis);

        assertNotNull(duration);
    }
}
