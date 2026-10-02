package org.apache.commons.compress.archivers.zip;

import static org.junit.Assert.assertTrue;

import java.nio.file.attribute.FileTime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class X5455_ExtendedTimestamp_ESTest_test34 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    /**
     * Setting a modify time as a {@link FileTime} must flag the modify-time bit
     * (bit 0) as present in the extra field.
     */
    @Test(timeout = 4000)
    public void settingModifyFileTimeMarksModifyTimeAsPresent() throws Throwable {
        X5455_ExtendedTimestamp extendedTimestamp = new X5455_ExtendedTimestamp();
        FileTime modifyTime = FileTime.fromMillis(2L);

        extendedTimestamp.setModifyFileTime(modifyTime);

        assertTrue(extendedTimestamp.isBit0_modifyTimePresent());
    }
}
