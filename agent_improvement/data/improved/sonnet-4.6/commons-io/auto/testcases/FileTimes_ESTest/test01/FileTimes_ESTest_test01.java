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
public class FileTimes_ESTest_test01 extends FileTimes_ESTest_scaffolding {

    // NTFS epoch starts at 1601-01-01; Unix epoch starts at 1970-01-01.
    // toNtfsTime converts Java milliseconds to 100-nanosecond intervals since 1601.
    @Test(timeout = 4000)
    public void test01_toNtfsTime_convertsJavaMillisToNtfs100NanosecondIntervals() throws Throwable {
        long javaTimeMillis = 286L;
        long ntfsTime = FileTimes.toNtfsTime(javaTimeMillis);
        // 286 ms * 10000 (100-ns per ms) + NTFS-to-Unix offset (116444736000000000) = 116444736002860000
        assertEquals(116444736002860000L, ntfsTime);
    }
}
