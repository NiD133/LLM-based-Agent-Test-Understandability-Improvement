package org.apache.commons.compress.archivers.zip;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.file.attribute.FileTime;
import java.util.concurrent.TimeUnit;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class X5455_ExtendedTimestamp_ESTest_test37 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void setAccessFileTime_withValidTime_setsAccessTimePresentFlag() throws Throwable {
        X5455_ExtendedTimestamp extendedTimestamp = new X5455_ExtendedTimestamp();

        // Create a FileTime representing 4 hours since epoch
        FileTime accessTime = FileTime.from(4L, TimeUnit.HOURS);
        extendedTimestamp.setAccessFileTime(accessTime);

        // Verify toString does not throw and that the access-time flag is set
        extendedTimestamp.toString();
        assertTrue(extendedTimestamp.isBit1_accessTimePresent());
    }
}
