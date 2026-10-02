package org.apache.commons.compress.archivers.zip;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.zip.ZipException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class X5455_ExtendedTimestamp_ESTest_test32 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    /**
     * parseFromLocalFileData rejects a negative length with a ZipException,
     * because the method requires at least 1 byte to read the flags field.
     */
    @Test(timeout = 4000)
    public void test32() throws Throwable {
        X5455_ExtendedTimestamp timestamp = new X5455_ExtendedTimestamp();
        byte[] centralDirData = timestamp.getCentralDirectoryData();

        try {
            // offset=-244, length=(byte)-55 → length is negative → too short
            timestamp.parseFromLocalFileData(centralDirData, (-244), (byte) (-55));
            fail("Expecting exception: ZipException");
        } catch (ZipException e) {
            verifyException("org.apache.commons.compress.archivers.zip.X5455_ExtendedTimestamp", e);
        }
    }
}
