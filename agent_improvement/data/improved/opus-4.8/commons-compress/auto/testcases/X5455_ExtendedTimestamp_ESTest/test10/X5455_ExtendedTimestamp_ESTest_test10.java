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
public class X5455_ExtendedTimestamp_ESTest_test10 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    /**
     * Parsing local file data whose flags byte has only the create-time bit set
     * should leave the field's flags equal to that bit, even when the buffer is too
     * short to actually hold the create-time value (so no timestamp is read).
     */
    @Test(timeout = 4000)
    public void parseFromLocalFileDataReadsCreateTimeFlag() throws Throwable {
        X5455_ExtendedTimestamp extendedTimestamp = new X5455_ExtendedTimestamp();

        // Two-byte buffer; the byte at the parse offset (index 1) is the flags byte,
        // set to the CREATE_TIME bit (value 4).
        byte[] localFileData = new byte[2];
        localFileData[1] = (byte) 4;

        int offset = 1;
        int length = 4;
        extendedTimestamp.parseFromLocalFileData(localFileData, offset, length);

        assertEquals((byte) 4, extendedTimestamp.getFlags());
    }
}
