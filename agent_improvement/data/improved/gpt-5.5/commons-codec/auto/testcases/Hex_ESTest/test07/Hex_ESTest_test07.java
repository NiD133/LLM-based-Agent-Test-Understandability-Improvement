package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Hex_ESTest_test07 extends Hex_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test07() throws Throwable {
        Hex decoder = new Hex();

        try {
            decoder.decode((Object) "]");
            fail("Expecting exception: Exception");
        } catch (Exception expectedException) {
            // A single hexadecimal character cannot be decoded because
            // Hex requires character pairs.
            verifyException("org.apache.commons.codec.binary.Hex", expectedException);
        }
    }
}
