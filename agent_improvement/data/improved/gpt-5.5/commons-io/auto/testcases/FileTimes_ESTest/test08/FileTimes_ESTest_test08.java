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
public class FileTimes_ESTest_test08 extends FileTimes_ESTest_scaffolding {

    private static final long NTFS_EPOCH_OFFSET_IN_100_NANOSECOND_UNITS = -116444736000000000L;

    @Test(timeout = 4000)
    public void test08() throws Throwable {
        MockDate ntfsEpochOffsetDate = new MockDate(NTFS_EPOCH_OFFSET_IN_100_NANOSECOND_UNITS);
        FileTime ntfsEpochOffsetFileTime = FileTimes.toFileTime(ntfsEpochOffsetDate);
        boolean isRepresentableAsUnixTime = FileTimes.isUnixTime(ntfsEpochOffsetFileTime);

        assertFalse(isRepresentableAsUnixTime);
    }
}
