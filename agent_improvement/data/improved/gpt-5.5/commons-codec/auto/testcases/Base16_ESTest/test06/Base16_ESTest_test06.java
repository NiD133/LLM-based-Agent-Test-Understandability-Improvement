package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.apache.commons.codec.CodecPolicy;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Base16_ESTest_test06 extends Base16_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test06() throws Throwable {
        Base16 base16 = new Base16();
        String malformedBase16Input = "E5SrU{4M(ZcJi?^<";

        try {
            base16.decode(malformedBase16Input);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.apache.commons.codec.binary.Base16", e);
        }
    }
}
