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
public class X5455_ExtendedTimestamp_ESTest_test26 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    /**
     * Verifies that setting a non-null create time via setCreateTime() marks bit2
     * (the create-time-present flag) as set, and that getCreateFileTime() can be
     * called without error.
     */
    @Test(timeout = 4000)
    public void test26() throws Throwable {
        // Arrange
        X5455_ExtendedTimestamp timestamp = new X5455_ExtendedTimestamp();
        ZipLong createTime = ZipLong.DD_SIG;

        // Act
        timestamp.setCreateTime(createTime);
        timestamp.getCreateFileTime();

        // Assert: setting a non-null create time must activate the create-time bit
        assertTrue(timestamp.isBit2_createTimePresent());
    }
}
