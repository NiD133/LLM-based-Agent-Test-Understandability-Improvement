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

    private static final String SINGLE_BASE58_CHARACTER = "X";
    private static final int INITIAL_BUFFER_SIZE = 76;
    private static final int INVALID_OFFSET = 64;
    private static final int INVALID_LENGTH = 64;

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        Base58 codec = new Base58();
        BaseNCodec.Context context = new BaseNCodec.Context();

        byte[] decodedSingleCharacter = codec.decode(SINGLE_BASE58_CHARACTER);
        codec.ensureBufferSize(INITIAL_BUFFER_SIZE, context);

        // EvoSuite captured this invalid range to document the thrown exception.
        try {
            codec.encode(decodedSingleCharacter, INVALID_OFFSET, INVALID_LENGTH, context);
            fail("Expecting exception: ArrayIndexOutOfBoundsException");
        } catch (ArrayIndexOutOfBoundsException e) {
        }
    }
}
