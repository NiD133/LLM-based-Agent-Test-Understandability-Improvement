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
public class X5455_ExtendedTimestamp_ESTest_test11 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    /**
     * Verifies that parseFromLocalFileData reads the flags byte from the given
     * offset. The flags byte is the first byte of the parsed region, so with an
     * offset of 2 the flags are taken from index 2 of the data array. That byte
     * is set to the ACCESS_TIME_BIT value (2), so getFlags() should return 2.
     */
    @Test(timeout = 4000)
    public void parseFromLocalFileDataReadsFlagsFromOffset() throws Throwable {
        X5455_ExtendedTimestamp extendedTimestamp = new X5455_ExtendedTimestamp();

        // The flags byte lives at the offset; set index 2 to the access-time flag.
        final int flagsOffset = 2;
        byte[] localFileData = new byte[20];
        localFileData[flagsOffset] = X5455_ExtendedTimestamp.ACCESS_TIME_BIT;

        extendedTimestamp.parseFromLocalFileData(localFileData, flagsOffset, 2212);

        assertEquals(X5455_ExtendedTimestamp.ACCESS_TIME_BIT, extendedTimestamp.getFlags());
    }
}
