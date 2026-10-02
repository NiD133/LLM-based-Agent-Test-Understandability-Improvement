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
public class X5455_ExtendedTimestamp_ESTest_test09 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    private static final int DATA_LENGTH = 3;
    private static final int FLAG_INDEX = 2;
    private static final byte CREATE_TIME_FLAG = (byte) 4;
    private static final byte CENTRAL_DIRECTORY_OFFSET = (byte) 2;
    private static final int CENTRAL_DIRECTORY_LENGTH = 25461;

    @Test(timeout = 4000)
    public void test09() throws Throwable {
        X5455_ExtendedTimestamp extendedTimestamp = new X5455_ExtendedTimestamp();
        byte[] centralDirectoryData = new byte[DATA_LENGTH];
        centralDirectoryData[FLAG_INDEX] = CREATE_TIME_FLAG;

        try {
            extendedTimestamp.parseFromCentralDirectoryData(
                    centralDirectoryData,
                    CENTRAL_DIRECTORY_OFFSET,
                    CENTRAL_DIRECTORY_LENGTH);
            fail("Expecting exception: ArrayIndexOutOfBoundsException");
        } catch (ArrayIndexOutOfBoundsException e) {
            verifyException("org.apache.commons.compress.utils.ByteUtils", e);
        }
    }
}
