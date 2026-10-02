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
public class FileTimes_ESTest_test07 extends FileTimes_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_isUnixTime_oneSecondBeforeEpoch_returnsTrue() throws Throwable {
        // -1 seconds represents one second before the Unix epoch (1969-12-31T23:59:59Z),
        // which is within the valid Unix time range [Integer.MIN_VALUE, Integer.MAX_VALUE].
        long oneSecondBeforeEpoch = -1L;
        boolean isValidUnixTime = FileTimes.isUnixTime(oneSecondBeforeEpoch);
        assertTrue(isValidUnixTime);
    }
}
