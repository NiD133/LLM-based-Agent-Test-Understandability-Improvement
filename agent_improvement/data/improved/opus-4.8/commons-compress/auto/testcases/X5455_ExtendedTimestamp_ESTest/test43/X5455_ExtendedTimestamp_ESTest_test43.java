package org.apache.commons.compress.archivers.zip;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.nio.file.attribute.FileTime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class X5455_ExtendedTimestamp_ESTest_test43 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    /**
     * Setting the create time to a null {@link FileTime} should leave the field
     * marked as "no create timestamp present", keeping the create-time flag bit
     * (and therefore the whole flags byte) cleared.
     */
    @Test(timeout = 4000)
    public void settingNullCreateFileTimeClearsCreateTimeFlag() throws Throwable {
        X5455_ExtendedTimestamp extendedTimestamp = new X5455_ExtendedTimestamp();

        extendedTimestamp.setCreateFileTime((FileTime) null);

        assertFalse(extendedTimestamp.isBit2_createTimePresent());
        assertEquals((byte) 0, extendedTimestamp.getFlags());
    }
}
