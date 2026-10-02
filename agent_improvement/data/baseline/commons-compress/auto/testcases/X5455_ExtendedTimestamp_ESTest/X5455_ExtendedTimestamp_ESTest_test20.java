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
public class X5455_ExtendedTimestamp_ESTest_test20 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test20() throws Throwable {
        X5455_ExtendedTimestamp x5455_ExtendedTimestamp0 = new X5455_ExtendedTimestamp();
        x5455_ExtendedTimestamp0.setFlags((byte) (-76));
        X5455_ExtendedTimestamp x5455_ExtendedTimestamp1 = new X5455_ExtendedTimestamp();
        ZipLong zipLong0 = ZipLong.DD_SIG;
        x5455_ExtendedTimestamp1.setCreateTime(zipLong0);
        boolean boolean0 = x5455_ExtendedTimestamp0.equals(x5455_ExtendedTimestamp1);
        assertTrue(x5455_ExtendedTimestamp1.isBit2_createTimePresent());
        assertFalse(boolean0);
    }
}
