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
public class X5455_ExtendedTimestamp_ESTest_test43 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    /**
     * Setting the create file time to null should leave bit2 (create-time present) unset
     * and keep the entire flags byte at zero.
     */
    @Test(timeout = 4000)
    public void test43_setCreateFileTimeNull_doesNotSetCreateTimeBitOrFlags() throws Throwable {
        X5455_ExtendedTimestamp timestamp = new X5455_ExtendedTimestamp();

        timestamp.setCreateFileTime((FileTime) null);

        assertFalse("bit2 (create time present) should be false when null FileTime is set",
                timestamp.isBit2_createTimePresent());
        assertEquals("flags byte should remain 0 when no timestamps are set",
                (byte) 0, timestamp.getFlags());
    }
}
