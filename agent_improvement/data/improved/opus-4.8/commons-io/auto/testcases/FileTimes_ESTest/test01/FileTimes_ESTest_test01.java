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

    /**
     * Verifies that {@link FileTimes#toNtfsTime(long)} converts a Java time
     * (milliseconds since the Unix epoch) into the corresponding NTFS time
     * (100-nanosecond intervals since 1 January 1601).
     */
    @Test(timeout = 4000)
    public void toNtfsTime_convertsJavaMillisToNtfsTime() throws Throwable {
        final long javaTimeMillis = 286L;

        final long ntfsTime = FileTimes.toNtfsTime(javaTimeMillis);

        assertEquals(116444736002860000L, ntfsTime);
    }
}
