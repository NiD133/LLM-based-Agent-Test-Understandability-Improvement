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

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        byte[] byteArray0 = new byte[2];
        X000A_NTFS x000A_NTFS0 = new X000A_NTFS();
        try {
            ExtraFieldUtils.fillExtraField(x000A_NTFS0, byteArray0, (-2), (byte) 11, false);
            fail("Expecting exception: ZipException");
        } catch (ZipException e) {
            //
            // Failed to parse corrupt ZIP extra field of type a
            //
            verifyException("org.apache.commons.compress.archivers.zip.ZipUtil", e);
        }
    }
}
