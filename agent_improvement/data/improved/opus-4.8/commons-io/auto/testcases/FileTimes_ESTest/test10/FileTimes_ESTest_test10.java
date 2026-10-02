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
public class FileTimes_ESTest_test10 extends FileTimes_ESTest_scaffolding {

    /**
     * Verifies that adding seconds to a FileTime produces a new, distinct FileTime
     * that is not equal to the original.
     */
    @Test(timeout = 4000)
    public void plusSecondsReturnsFileTimeNotEqualToOriginal() throws Throwable {
        final long unixToNtfsOffsetMillis = -116444736000000000L;
        final long secondsToAdd = 172L;

        Date date = new MockDate(unixToNtfsOffsetMillis);
        FileTime originalFileTime = FileTimes.toFileTime(date);
        FileTime shiftedFileTime = FileTimes.plusSeconds(originalFileTime, secondsToAdd);

        assertFalse("Shifting a FileTime by 172 seconds must yield a different FileTime",
                shiftedFileTime.equals((Object) originalFileTime));
    }
}
