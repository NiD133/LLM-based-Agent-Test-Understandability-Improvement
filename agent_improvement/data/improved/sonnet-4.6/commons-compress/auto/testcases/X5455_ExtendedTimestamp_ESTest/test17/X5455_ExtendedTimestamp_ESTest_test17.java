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
public class X5455_ExtendedTimestamp_ESTest_test17 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    /**
     * Setting a non-null create time via setCreateTime() must activate bit2 of the flags byte
     * (bit2_createTimePresent = true). Calling getCentralDirectoryData() afterward must not clear
     * that flag — the assertion confirms the flag remains set.
     */
    @Test(timeout = 4000)
    public void test_setCreateTime_activatesCreateTimeFlag() throws Throwable {
        X5455_ExtendedTimestamp extendedTimestamp = new X5455_ExtendedTimestamp();
        ZipLong createTime = ZipLong.ZIP64_MAGIC;

        extendedTimestamp.setCreateTime(createTime);
        extendedTimestamp.getCentralDirectoryData();

        assertTrue("bit2_createTimePresent should be true after setCreateTime() with a non-null value",
                extendedTimestamp.isBit2_createTimePresent());
    }
}
