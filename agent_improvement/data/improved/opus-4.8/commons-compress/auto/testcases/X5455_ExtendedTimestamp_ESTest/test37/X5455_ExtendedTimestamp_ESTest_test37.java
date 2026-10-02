package org.apache.commons.compress.archivers.zip;

import org.junit.Test;
import static org.junit.Assert.*;
import java.nio.file.attribute.FileTime;
import java.util.concurrent.TimeUnit;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class X5455_ExtendedTimestamp_ESTest_test37 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    /**
     * Setting an access time via {@link X5455_ExtendedTimestamp#setAccessFileTime(FileTime)}
     * should flag the access timestamp as present (bit 1 of the flags byte).
     */
    @Test(timeout = 4000)
    public void settingAccessFileTimeMarksAccessTimeAsPresent() throws Throwable {
        X5455_ExtendedTimestamp extendedTimestamp = new X5455_ExtendedTimestamp();
        FileTime accessTime = FileTime.from(4L, TimeUnit.HOURS);

        extendedTimestamp.setAccessFileTime(accessTime);

        // toString() exercises the debug-rendering path now that an access time is set.
        extendedTimestamp.toString();
        assertTrue(extendedTimestamp.isBit1_accessTimePresent());
    }
}
