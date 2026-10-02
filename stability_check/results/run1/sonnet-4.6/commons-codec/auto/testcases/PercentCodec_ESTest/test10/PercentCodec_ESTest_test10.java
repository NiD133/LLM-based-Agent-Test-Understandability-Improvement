package org.apache.commons.codec.net;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PercentCodec_ESTest_test10 extends PercentCodec_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test10() throws Throwable {
        // A 5-element array of zero bytes serves as both the set of characters to
        // always encode and the byte sequence to be decoded. Zero bytes are not
        // percent-encoded sequences (they don't start with '%'), so decoding them
        // returns the same bytes unchanged.
        byte[] zeroBytesInput = new byte[5];
        PercentCodec percentCodec = new PercentCodec(zeroBytesInput, false);

        byte[] decodedBytes = percentCodec.decode(zeroBytesInput);

        assertArrayEquals(new byte[5], decodedBytes);
    }
}
