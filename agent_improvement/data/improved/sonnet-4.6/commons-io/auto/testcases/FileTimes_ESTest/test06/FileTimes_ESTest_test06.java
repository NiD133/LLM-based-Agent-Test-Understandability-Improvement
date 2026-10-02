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

    // The NTFS epoch offset in 100-nanosecond intervals: distance from 1601-01-01 to Unix epoch.
    // Treated here as a raw second count, it is far beyond Integer.MAX_VALUE (~2.1 billion),
    // so it cannot be represented as a valid Unix timestamp.
    private static final long NTFS_EPOCH_OFFSET_AS_SECONDS = 116444736000000000L;

    @Test(timeout = 4000)
    public void test_isUnixTime_returnsFalse_whenValueExceedsIntegerRange() throws Throwable {
        boolean result = FileTimes.isUnixTime(NTFS_EPOCH_OFFSET_AS_SECONDS);
        assertFalse(result);
    }
}
