package org.apache.commons.codec.net;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PercentCodec_ESTest_test05 extends PercentCodec_ESTest_scaffolding {

    // PercentCodec.encode(byte[]) returns null when the input is null, matching
    // the general contract for binary encoders that treat null as a no-op.
    @Test(timeout = 4000)
    public void test05_encodeNullByteArray_returnsNull() throws Throwable {
        PercentCodec percentCodec = new PercentCodec();
        byte[] result = percentCodec.encode((byte[]) null);
        assertNull(result);
    }
}
