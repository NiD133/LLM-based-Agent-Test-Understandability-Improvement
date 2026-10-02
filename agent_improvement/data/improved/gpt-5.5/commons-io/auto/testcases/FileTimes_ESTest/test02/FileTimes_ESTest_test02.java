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
public class FileTimes_ESTest_test02 extends FileTimes_ESTest_scaffolding {

    private static final long JAVA_TIME_BEFORE_NTFS_RANGE = -116444736000000000L;
    private static final long MINIMUM_NTFS_TIME = -9223372036854775808L;

    @Test(timeout = 4000)
    public void test02() throws Throwable {
        MockDate dateBeforeRepresentableNtfsRange = new MockDate(JAVA_TIME_BEFORE_NTFS_RANGE);

        long ntfsTime = FileTimes.toNtfsTime((Date) dateBeforeRepresentableNtfsRange);

        assertEquals(MINIMUM_NTFS_TIME, ntfsTime);
    }
}
