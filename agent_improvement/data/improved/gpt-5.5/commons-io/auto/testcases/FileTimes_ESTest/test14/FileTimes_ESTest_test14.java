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

    @Test(timeout = 4000)
    public void test14() throws Throwable {
        final long nanosAfterEpoch = 1070L;
        final TimeUnit precision = TimeUnit.NANOSECONDS;
        final FileTime fileTime = FileTime.from(nanosAfterEpoch, precision);

        final long ntfsTime = FileTimes.toNtfsTime(fileTime);

        assertEquals(116444736000000010L, ntfsTime);
    }
}
