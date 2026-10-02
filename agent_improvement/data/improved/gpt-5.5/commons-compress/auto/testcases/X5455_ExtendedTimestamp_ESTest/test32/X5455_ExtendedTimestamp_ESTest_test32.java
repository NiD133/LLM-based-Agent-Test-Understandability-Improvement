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
public class X5455_ExtendedTimestamp_ESTest_test32 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    private static final int NEGATIVE_OFFSET = -244;
    private static final int NEGATIVE_LENGTH = -55;

    @Test(timeout = 4000)
    public void test32() throws Throwable {
        X5455_ExtendedTimestamp timestampField = new X5455_ExtendedTimestamp();
        byte[] centralDirectoryData = timestampField.getCentralDirectoryData();

        try {
            timestampField.parseFromLocalFileData(centralDirectoryData, NEGATIVE_OFFSET, (byte) NEGATIVE_LENGTH);
            fail("Expecting exception: ZipException");
        } catch (ZipException e) {
            //
            // X5455_ExtendedTimestamp too short, only -55 bytes
            //
            verifyException("org.apache.commons.compress.archivers.zip.X5455_ExtendedTimestamp", e);
        }
    }
}
