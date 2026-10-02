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
public class FileTimes_ESTest_test09 extends FileTimes_ESTest_scaffolding {

    /**
     * Verifies that shifting a FileTime by a large number of nanoseconds yields
     * a new, distinct FileTime that is not equal to the original.
     */
    @Test(timeout = 4000)
    public void plusNanosWithLargeOffsetReturnsDifferentFileTime() throws Throwable {
        final long ntfsTime = -3078L;
        final long nanosToAdd = -116444736000000000L;

        FileTime originalFileTime = FileTimes.ntfsTimeToFileTime(ntfsTime);
        FileTime shiftedFileTime = FileTimes.plusNanos(originalFileTime, nanosToAdd);

        assertFalse(
                "Shifting by a non-zero nanosecond offset should produce a different FileTime",
                shiftedFileTime.equals((Object) originalFileTime));
    }
}
