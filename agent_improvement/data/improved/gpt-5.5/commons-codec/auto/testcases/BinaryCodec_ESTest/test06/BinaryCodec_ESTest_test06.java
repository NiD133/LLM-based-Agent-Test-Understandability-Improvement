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

    @Test(timeout = 4000)
    public void testNullStringDecodesToSharedEmptyByteArray() throws Throwable {
        BinaryCodec codec = new BinaryCodec();

        byte[] decodedNullString = codec.toByteArray((String) null);
        byte[] encodedEmptyBytes = BinaryCodec.toAsciiBytes(decodedNullString);

        assertSame(encodedEmptyBytes, decodedNullString);
    }
}
