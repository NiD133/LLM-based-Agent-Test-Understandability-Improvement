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
public class X5455_ExtendedTimestamp_ESTest_test02 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    private static final byte FOUR_MILLISECONDS_AFTER_EPOCH = (byte) 4;

    @Test(timeout = 4000)
    public void test02() throws Throwable {
        X5455_ExtendedTimestamp timestampField = new X5455_ExtendedTimestamp();
        Instant modifyInstant = MockInstant.ofEpochMilli(FOUR_MILLISECONDS_AFTER_EPOCH);
        Date modifyDate = Date.from(modifyInstant);

        timestampField.setModifyJavaTime(modifyDate);
        timestampField.getCentralDirectoryData();

        assertTrue(timestampField.isBit0_modifyTimePresent());
    }
}
