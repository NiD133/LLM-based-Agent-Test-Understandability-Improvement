package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BinaryCodec_ESTest_test03 extends BinaryCodec_ESTest_scaffolding {

    /**
     * Verifies that decoding a null Object input returns an empty (non-null) byte array
     * rather than throwing an exception or returning null.
     */
    @Test(timeout = 4000)
    public void test03() throws Throwable {
        BinaryCodec codec = new BinaryCodec();
        Object result = codec.decode((Object) null);
        assertNotNull("decode(null) should return an empty byte array, not null", result);
    }
}
