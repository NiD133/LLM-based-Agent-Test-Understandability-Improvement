package org.apache.commons.compress.archivers.zip;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class X5455_ExtendedTimestamp_ESTest_test17 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    /**
     * Setting a create time flips on the "create time present" flag (bit 2),
     * and that flag stays set even after producing the central directory data.
     */
    @Test(timeout = 4000)
    public void settingCreateTimeMarksCreateTimePresent() throws Throwable {
        X5455_ExtendedTimestamp extendedTimestamp = new X5455_ExtendedTimestamp();

        extendedTimestamp.setCreateTime(ZipLong.ZIP64_MAGIC);
        extendedTimestamp.getCentralDirectoryData();

        assertTrue(extendedTimestamp.isBit2_createTimePresent());
    }
}
