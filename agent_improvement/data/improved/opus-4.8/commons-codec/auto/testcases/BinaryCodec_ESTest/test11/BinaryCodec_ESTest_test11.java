package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BinaryCodec_ESTest_test11 extends BinaryCodec_ESTest_scaffolding {

    /**
     * Decoding an empty String yields the shared empty byte array, and decoding
     * that empty byte array again yields the very same array instance. Both the
     * empty-String and empty-byte[] inputs map to BinaryCodec's single internal
     * EMPTY_BYTE_ARRAY constant, so the two results are reference-identical.
     */
    @Test(timeout = 4000)
    public void decodingEmptyInputsReturnsSameEmptyByteArrayInstance() throws Throwable {
        BinaryCodec binaryCodec = new BinaryCodec();

        Object decodedFromEmptyString = binaryCodec.decode((Object) "");
        Object decodedFromEmptyBytes = binaryCodec.decode(decodedFromEmptyString);

        assertSame(decodedFromEmptyString, decodedFromEmptyBytes);
    }
}
