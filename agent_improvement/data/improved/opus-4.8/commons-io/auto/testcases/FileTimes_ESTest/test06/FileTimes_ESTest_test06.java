package org.apache.commons.io.file.attribute;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.util.MockDate;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class FileTimes_ESTest_test06 extends FileTimes_ESTest_scaffolding {

    /**
     * {@link FileTimes#isUnixTime(long)} considers a seconds value to be a valid Unix
     * time only when it fits within the {@code int} range
     * ({@code Integer.MIN_VALUE..Integer.MAX_VALUE}). A value far larger than
     * {@code Integer.MAX_VALUE} must therefore be rejected.
     */
    @Test(timeout = 4000)
    public void isUnixTime_returnsFalse_forSecondsExceedingIntegerRange() throws Throwable {
        // Seconds value well beyond Integer.MAX_VALUE, so it cannot be a Unix time.
        long secondsBeyondIntegerRange = 116444736000000000L;

        boolean isUnixTime = FileTimes.isUnixTime(secondsBeyondIntegerRange);

        assertFalse(isUnixTime);
    }
}
