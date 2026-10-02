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
public class FileTimes_ESTest_test14 extends FileTimes_ESTest_scaffolding {

    // NTFS epoch starts at 1601-01-01; this offset (in 100-ns intervals) bridges it to the Unix epoch.
    private static final long UNIX_TO_NTFS_OFFSET_MAGNITUDE = 116444736000000000L;

    @Test(timeout = 4000)
    public void test_toNtfsTime_fileTimeOf1070Nanoseconds_returnsCorrectNtfsValue() throws Throwable {
        // 1070 ns since Unix epoch => 10 hundred-nanosecond intervals (floor of 1070/100)
        FileTime fileTimeAt1070Nanos = FileTime.from(1070L, TimeUnit.NANOSECONDS);

        long ntfsTime = FileTimes.toNtfsTime(fileTimeAt1070Nanos);

        // Expected: NTFS offset + 10 hundred-nanosecond intervals = 116444736000000010
        assertEquals(116444736000000010L, ntfsTime);
    }
}
