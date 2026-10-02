package org.apache.commons.compress.archivers.zip;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class X5455_ExtendedTimestamp_ESTest_test36 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void getCreateTime_returnsNull_whenNoCreateTimeSet() throws Throwable {
        // A newly constructed instance has no timestamps set, so createTime should be null.
        X5455_ExtendedTimestamp extendedTimestamp = new X5455_ExtendedTimestamp();
        ZipLong createTime = extendedTimestamp.getCreateTime();
        assertNull(createTime);
    }
}
