package org.apache.commons.compress.compressors.zstandard;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ZstdUtils_ESTest_test2 extends ZstdUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test2() throws Throwable {
        byte[] candidateSignature = new byte[8];
        candidateSignature[0] = (byte) 83;
        candidateSignature[1] = (byte) 42;

        boolean matchesZstandardSignature = ZstdUtils.matches(candidateSignature, (byte) 42);

        assertFalse(matchesZstandardSignature);
    }
}
