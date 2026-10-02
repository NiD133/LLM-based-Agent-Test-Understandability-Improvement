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
public class FileTimes_ESTest_test12 extends FileTimes_ESTest_scaffolding {

    // 3 hundred-nanosecond intervals below the Windows-to-Unix epoch offset,
    // representing a date in the pre-Unix-epoch medieval period (year 1231).
    private static final long NTFS_TIME_BEFORE_WINDOWS_EPOCH_OFFSET = -116444736000000003L;

    @Test(timeout = 4000)
    public void testNtfsTimeToDate_withNegativeNtfsTimestamp_returnsDateInYear1231() throws Throwable {
        Date date = FileTimes.ntfsTimeToDate(NTFS_TIME_BEFORE_WINDOWS_EPOCH_OFFSET);
        assertEquals("Expected NTFS time below the Windows-to-Unix epoch offset to map to Dec 25, year 1231",
                "Thu Dec 25 23:59:59 GMT 1231", date.toString());
    }
}
