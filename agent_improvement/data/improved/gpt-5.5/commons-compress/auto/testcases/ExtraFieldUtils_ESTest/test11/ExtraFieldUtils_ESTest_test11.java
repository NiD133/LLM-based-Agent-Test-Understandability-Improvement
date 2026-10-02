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
        final int malformedExtraFieldLength = 8;
        final int claimedBlockLengthOffset = 2;
        final byte claimedBlockLength = (byte) 35;

        byte[] malformedExtraField = new byte[malformedExtraFieldLength];
        malformedExtraField[claimedBlockLengthOffset] = claimedBlockLength;

        try {
            ExtraFieldUtils.parse(malformedExtraField);
            fail("Expecting exception: ZipException");
        } catch (ZipException e) {
            //
            // Bad extra field starting at 0.  Block length of 35 bytes exceeds remaining data of 4 bytes.
            //
            verifyException("org.apache.commons.compress.archivers.zip.ExtraFieldUtils$UnparseableExtraField", e);
        }
    }
}
