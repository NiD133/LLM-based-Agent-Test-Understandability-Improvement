package org.apache.commons.io.file.attribute;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class FileTimes_ESTest_test07 extends FileTimes_ESTest_scaffolding {

    /**
     * Verifies that {@link FileTimes#isUnixTime(long)} accepts a seconds value
     * that lies within the representable Unix time range
     * ({@code Integer.MIN_VALUE..Integer.MAX_VALUE}).
     *
     * <p>{@code -1} second sits comfortably inside that range, so the method
     * should report it as a valid Unix time.</p>
     */
    @Test(timeout = 4000)
    public void isUnixTime_returnsTrue_forSecondsWithinIntegerRange() throws Throwable {
        long secondsWithinRange = -1L;

        boolean isValidUnixTime = FileTimes.isUnixTime(secondsWithinRange);

        assertTrue("Seconds inside the int range should be a valid Unix time", isValidUnixTime);
    }
}
