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
public class X5455_ExtendedTimestamp_ESTest_test19 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    /**
     * Setting a non-null access time must activate ACCESS_TIME_BIT (bit 1 = 0x02) in the flags byte,
     * and getCentralDirectoryData() must succeed without error.
     */
    @Test(timeout = 4000)
    public void test19() throws Throwable {
        X5455_ExtendedTimestamp extendedTimestamp = new X5455_ExtendedTimestamp();

        // Use the well-known LFH_SIG ZipLong as a representative non-null access time value.
        ZipLong accessTimeValue = ZipLong.LFH_SIG;
        extendedTimestamp.setAccessTime(accessTimeValue);

        // Central directory data should be retrievable without error after setting access time.
        extendedTimestamp.getCentralDirectoryData();

        // Setting a non-null access time must set ACCESS_TIME_BIT (0x02) in the flags byte.
        assertEquals((byte) X5455_ExtendedTimestamp.ACCESS_TIME_BIT, extendedTimestamp.getFlags());
    }
}
