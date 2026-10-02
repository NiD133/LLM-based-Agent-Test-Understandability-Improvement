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
public class X5455_ExtendedTimestamp_ESTest_test04 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    /**
     * Verifies that toString() does not throw when the access-time flag is set
     * but no actual access time value has been stored, and that getFlags() returns
     * the exact flags byte that was set.
     */
    @Test(timeout = 4000)
    public void test04() throws Throwable {
        X5455_ExtendedTimestamp extendedTimestamp = new X5455_ExtendedTimestamp();

        // Enable only bit 1 (access-time present), leaving access time value as null
        extendedTimestamp.setFlags(X5455_ExtendedTimestamp.ACCESS_TIME_BIT);

        // toString() must handle a set access-time flag with no corresponding timestamp value
        extendedTimestamp.toString();

        assertEquals(X5455_ExtendedTimestamp.ACCESS_TIME_BIT, extendedTimestamp.getFlags());
    }
}
