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
public class X5455_ExtendedTimestamp_ESTest_test39 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test39() throws Throwable {
        X5455_ExtendedTimestamp timestampFromJavaDate = new X5455_ExtendedTimestamp();
        Instant epochInstant = MockInstant.ofEpochMilli((byte) 4);
        Date modifyDate = Date.from(epochInstant);
        timestampFromJavaDate.setModifyJavaTime(modifyDate);

        X5455_ExtendedTimestamp timestampFromFileTime = new X5455_ExtendedTimestamp();
        TimeUnit fileTimeUnit = TimeUnit.DAYS;
        FileTime twoDaysAfterEpoch = FileTime.from((long) (byte) 2, fileTimeUnit);
        timestampFromFileTime.setModifyFileTime(twoDaysAfterEpoch);

        boolean timestampsAreEqual = timestampFromJavaDate.equals(timestampFromFileTime);

        assertTrue(timestampFromJavaDate.isBit0_modifyTimePresent());
        assertFalse(timestampsAreEqual);
    }
}
