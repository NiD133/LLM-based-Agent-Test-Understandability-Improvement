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
public class ExtraFieldUtils_ESTest_test01 extends ExtraFieldUtils_ESTest_scaffolding {

    // fillExtraField wraps ArrayIndexOutOfBoundsException (from a negative offset) into ZipException
    @Test(timeout = 4000)
    public void test01() throws Throwable {
        byte[] twoByteData = new byte[2];
        X000A_NTFS ntfsField = new X000A_NTFS();
        int negativeOffset = -2;
        int length = (byte) 11;
        boolean isCentralDirectory = false;

        try {
            ExtraFieldUtils.fillExtraField(ntfsField, twoByteData, negativeOffset, length, isCentralDirectory);
            fail("Expecting exception: ZipException");
        } catch (ZipException e) {
            //
            // Failed to parse corrupt ZIP extra field of type a
            //
            verifyException("org.apache.commons.compress.archivers.zip.ZipUtil", e);
        }
    }
}
