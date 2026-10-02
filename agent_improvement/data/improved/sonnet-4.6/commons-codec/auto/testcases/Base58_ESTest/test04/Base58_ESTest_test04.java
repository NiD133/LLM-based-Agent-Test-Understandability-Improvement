package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Base58_ESTest_test04 extends Base58_ESTest_scaffolding {

    /**
     * Verifies that after signaling EOF via a negative length on the first decode call,
     * subsequent decode calls are ignored and isStrictDecoding() returns false.
     */
    @Test(timeout = 4000)
    public void test04() throws Throwable {
        Base58 codec = new Base58();
        byte[] inputData = new byte[5];
        BaseNCodec.Context context = new BaseNCodec.Context();

        // A negative length signals EOF to the decoder; the buffer is empty so no
        // conversion occurs, but context.eof is set to true.
        int eofLength = -98;
        codec.decode(inputData, 95, eofLength, context);

        // Because context.eof is now true, this second call is a no-op.
        codec.decode(inputData, 88, 71, context);

        assertFalse(codec.isStrictDecoding());
    }
}
