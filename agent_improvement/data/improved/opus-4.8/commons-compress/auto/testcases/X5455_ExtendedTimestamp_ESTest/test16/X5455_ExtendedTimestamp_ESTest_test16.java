package org.apache.commons.compress.archivers.zip;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class X5455_ExtendedTimestamp_ESTest_test16 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    /**
     * Setting the flags byte to the "create time" bit, then serializing the local
     * file data, must leave the flags byte untouched and equal to the value we set.
     */
    @Test(timeout = 4000)
    public void settingCreateTimeBitKeepsFlagsUnchangedAfterSerialization() throws Throwable {
        X5455_ExtendedTimestamp extendedTimestamp = new X5455_ExtendedTimestamp();

        extendedTimestamp.setFlags(X5455_ExtendedTimestamp.CREATE_TIME_BIT);
        extendedTimestamp.getLocalFileDataData();

        assertEquals(X5455_ExtendedTimestamp.CREATE_TIME_BIT, extendedTimestamp.getFlags());
    }
}
