package org.apache.commons.compress.archivers.zip;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class X5455_ExtendedTimestamp_ESTest_test26 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    /**
     * Setting a non-null create time should flip on the "create time present"
     * flag (bit 2), and the create time should then be readable as a FileTime.
     */
    @Test(timeout = 4000)
    public void settingCreateTimeMarksCreateTimeAsPresent() throws Throwable {
        X5455_ExtendedTimestamp extendedTimestamp = new X5455_ExtendedTimestamp();

        extendedTimestamp.setCreateTime(ZipLong.DD_SIG);
        extendedTimestamp.getCreateFileTime();

        assertTrue(extendedTimestamp.isBit2_createTimePresent());
    }
}
