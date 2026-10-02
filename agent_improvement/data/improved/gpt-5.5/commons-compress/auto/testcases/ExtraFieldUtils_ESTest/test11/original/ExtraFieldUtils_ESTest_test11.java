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
public class ExtraFieldUtils_ESTest_test11 extends ExtraFieldUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test11() throws Throwable {
        byte[] byteArray0 = new byte[8];
        byteArray0[2] = (byte) 35;
        try {
            ExtraFieldUtils.parse(byteArray0);
            fail("Expecting exception: ZipException");
        } catch (ZipException e) {
            //
            // Bad extra field starting at 0.  Block length of 35 bytes exceeds remaining data of 4 bytes.
            //
            verifyException("org.apache.commons.compress.archivers.zip.ExtraFieldUtils$UnparseableExtraField", e);
        }
    }
}
