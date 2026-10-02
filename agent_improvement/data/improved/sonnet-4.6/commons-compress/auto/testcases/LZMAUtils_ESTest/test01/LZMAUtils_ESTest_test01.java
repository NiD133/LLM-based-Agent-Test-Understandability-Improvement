package org.apache.commons.compress.compressors.lzma;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LZMAUtils_ESTest_test01 extends LZMAUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test01_matchesReturnsTrueWhenSignatureStartsWithLzmaMagicBytes() throws Throwable {
        // LZMA magic header is { 0x5D, 0x00, 0x00 }; remaining bytes stay at default 0
        byte[] lzmaSignature = new byte[6];
        lzmaSignature[0] = (byte) 0x5D;
        boolean matchesLzmaFormat = LZMAUtils.matches(lzmaSignature, 3);
        assertTrue(matchesLzmaFormat);
    }
}
