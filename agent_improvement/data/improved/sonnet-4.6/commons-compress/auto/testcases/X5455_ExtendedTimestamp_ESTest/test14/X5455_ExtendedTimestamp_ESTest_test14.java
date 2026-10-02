package org.apache.commons.compress.archivers.zip;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.evosuite.runtime.mock.java.util.MockDate;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class X5455_ExtendedTimestamp_ESTest_test14 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    /**
     * Verifies that calling setCreateTime with a non-null ZipLong sets the create-time-present flag (bit2),
     * and that hashCode() executes without error when the create time is populated.
     */
    @Test(timeout = 4000)
    public void test14() throws Throwable {
        X5455_ExtendedTimestamp extendedTimestamp = new X5455_ExtendedTimestamp();

        // Use ZipLong.CFH_SIG (the Central File Header signature constant) as a stand-in
        // non-null ZipLong value to supply to setCreateTime.
        ZipLong createTimeValue = ZipLong.CFH_SIG;
        extendedTimestamp.setCreateTime(createTimeValue);

        // hashCode() exercises the createTime field internally; calling it confirms no exception is thrown.
        extendedTimestamp.hashCode();

        // setCreateTime with a non-null argument must flip bit2_createTimePresent to true.
        assertTrue(extendedTimestamp.isBit2_createTimePresent());
    }
}
