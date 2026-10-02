package org.apache.commons.compress.archivers.zip;

import org.junit.Test;
import static org.junit.Assert.*;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class X5455_ExtendedTimestamp_ESTest_test39 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test39() throws Throwable {
        // First timestamp: modify time set via java.util.Date (4 milliseconds after epoch)
        X5455_ExtendedTimestamp timestampWithDateModifyTime = new X5455_ExtendedTimestamp();
        Instant instant = MockInstant.ofEpochMilli((byte) 4);
        Date modifyDate = Date.from(instant);
        timestampWithDateModifyTime.setModifyJavaTime(modifyDate);

        // Second timestamp: modify time set via FileTime (2 days after epoch)
        X5455_ExtendedTimestamp timestampWithFileTimeModifyTime = new X5455_ExtendedTimestamp();
        FileTime modifyFileTime = FileTime.from((long) (byte) 2, TimeUnit.DAYS);
        timestampWithFileTimeModifyTime.setModifyFileTime(modifyFileTime);

        // Timestamps with different modify times should not be equal,
        // and setting a modify time marks bit0 as present
        boolean areEqual = timestampWithDateModifyTime.equals(timestampWithFileTimeModifyTime);
        assertTrue(timestampWithDateModifyTime.isBit0_modifyTimePresent());
        assertFalse(areEqual);
    }
}
