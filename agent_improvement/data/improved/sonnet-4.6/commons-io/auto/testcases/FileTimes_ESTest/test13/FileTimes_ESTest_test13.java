package org.apache.commons.io.file.attribute;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.file.attribute.FileTime;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class FileTimes_ESTest_test13 extends FileTimes_ESTest_scaffolding {

    /**
     * Verifies that {@link FileTimes#plusMillis} returns a different {@link FileTime}
     * when a positive number of milliseconds is added. Uses {@code Integer.MAX_VALUE}
     * as both the base time and the amount to add so that the result clearly overflows
     * the original value, confirming that the method does not return the same instance.
     */
    @Test(timeout = 4000)
    public void test_plusMillis_returnsDistinctFileTime_whenMillisecondsAreAdded() throws Throwable {
        long baseMillis = 2147483647L; // Integer.MAX_VALUE millis since epoch
        long millisToAdd = 2147483647L;

        FileTime originalTime = FileTime.fromMillis(baseMillis);
        FileTime adjustedTime = FileTimes.plusMillis(originalTime, millisToAdd);

        assertFalse(adjustedTime.equals((Object) originalTime));
    }
}
