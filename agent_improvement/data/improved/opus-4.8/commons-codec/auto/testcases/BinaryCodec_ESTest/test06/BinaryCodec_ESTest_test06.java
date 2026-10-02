package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BinaryCodec_ESTest_test06 extends BinaryCodec_ESTest_scaffolding {

    /**
     * Decoding a null String yields the shared empty byte array, and encoding
     * that empty array with toAsciiBytes returns the very same shared instance.
     * Both calls short-circuit on empty/null input, so no new array is allocated.
     */
    @Test(timeout = 4000)
    public void emptyInputsShareSameEmptyByteArrayInstance() throws Throwable {
        BinaryCodec binaryCodec = new BinaryCodec();

        byte[] decodedFromNull = binaryCodec.toByteArray((String) null);
        byte[] reEncodedAsAscii = BinaryCodec.toAsciiBytes(decodedFromNull);

        assertSame(decodedFromNull, reEncodedAsAscii);
    }
}
