package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Base58_ESTest_test01 extends Base58_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        Base58 codec = new Base58();
        BaseNCodec.Context context = new BaseNCodec.Context();

        byte[] decodedSingleByte = codec.decode("X");
        codec.ensureBufferSize(76, context);

        // The one-byte decoded input is intentionally encoded from offset 64,
        // preserving the generated test's expected out-of-bounds behavior.
        try {
            codec.encode(decodedSingleByte, 64, 64, context);
            fail("Expecting exception: ArrayIndexOutOfBoundsException");
        } catch (ArrayIndexOutOfBoundsException e) {
        }
    }
}
