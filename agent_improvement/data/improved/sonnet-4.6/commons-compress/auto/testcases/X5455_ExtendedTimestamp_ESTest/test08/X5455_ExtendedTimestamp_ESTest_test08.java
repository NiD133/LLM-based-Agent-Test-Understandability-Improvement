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
public class X5455_ExtendedTimestamp_ESTest_test08 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    /**
     * Setting the access time to null should clear the access-time present flag (bit1)
     * and leave the flags byte at zero, indicating no timestamps are stored.
     */
    @Test(timeout = 4000)
    public void test08() throws Throwable {
        X5455_ExtendedTimestamp extendedTimestamp = new X5455_ExtendedTimestamp();

        // A null Date means "no access time" — expect the flag bit to be cleared
        extendedTimestamp.setAccessJavaTime((Date) null);

        assertFalse(extendedTimestamp.isBit1_accessTimePresent());
        assertEquals((byte) 0, extendedTimestamp.getFlags());
    }
}
