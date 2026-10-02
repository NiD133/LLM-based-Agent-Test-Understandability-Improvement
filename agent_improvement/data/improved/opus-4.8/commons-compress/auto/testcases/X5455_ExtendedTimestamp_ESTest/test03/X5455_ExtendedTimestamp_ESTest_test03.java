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
public class X5455_ExtendedTimestamp_ESTest_test03 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    /**
     * Verifies that setting a non-null create time marks the "create time present"
     * flag bit (bit 2) as set. Per the CUT, {@code setCreateTime} sets
     * {@code bit2_createTimePresent} to true whenever a non-null ZipLong is supplied.
     * The {@code toString()} call exercises the debug-string rendering path for an
     * instance that carries only a create timestamp.
     */
    @Test(timeout = 4000)
    public void setCreateTimeWithNonNullValueMarksCreateTimePresent() throws Throwable {
        X5455_ExtendedTimestamp extendedTimestamp = new X5455_ExtendedTimestamp();

        // Any non-null ZipLong works here; CFH_SIG is a readily available constant.
        ZipLong createTime = ZipLong.CFH_SIG;
        extendedTimestamp.setCreateTime(createTime);

        // Exercise the debug-string rendering for an instance holding a create time.
        extendedTimestamp.toString();

        assertTrue("Setting a non-null create time should mark bit 2 as present",
                extendedTimestamp.isBit2_createTimePresent());
    }
}
