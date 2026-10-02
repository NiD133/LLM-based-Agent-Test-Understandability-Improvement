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

    @Test(timeout = 4000)
    public void test09() throws Throwable {
        X5455_ExtendedTimestamp x5455_ExtendedTimestamp0 = new X5455_ExtendedTimestamp();
        byte[] byteArray0 = new byte[3];
        byteArray0[2] = (byte) 4;
        // Undeclared exception!
        try {
            x5455_ExtendedTimestamp0.parseFromCentralDirectoryData(byteArray0, (byte) 2, 25461);
            fail("Expecting exception: ArrayIndexOutOfBoundsException");
        } catch (ArrayIndexOutOfBoundsException e) {
            //
            // 3
            //
            verifyException("org.apache.commons.compress.utils.ByteUtils", e);
        }
    }
}
