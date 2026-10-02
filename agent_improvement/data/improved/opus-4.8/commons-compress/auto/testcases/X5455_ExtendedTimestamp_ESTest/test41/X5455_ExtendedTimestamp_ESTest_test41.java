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
public class X5455_ExtendedTimestamp_ESTest_test41 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    /**
     * Setting the create time to a null Date must clear the create-time information:
     * the create-time flag bit must stay unset and the overall flags byte must remain 0.
     */
    @Test(timeout = 4000)
    public void settingNullCreateJavaTimeLeavesCreateTimeAbsent() throws Throwable {
        X5455_ExtendedTimestamp extendedTimestamp = new X5455_ExtendedTimestamp();

        extendedTimestamp.setCreateJavaTime((Date) null);

        assertEquals((byte) 0, extendedTimestamp.getFlags());
        assertFalse(extendedTimestamp.isBit2_createTimePresent());
    }
}
