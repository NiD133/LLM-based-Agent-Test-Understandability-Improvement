package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.apache.commons.codec.CodecPolicy;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Base16_ESTest_test00 extends Base16_ESTest_scaffolding {

    private static final int DECODE_OFFSET = 5;
    private static final int DECODE_LENGTH = 1767;
    private static final int INITIAL_WORK_AREA = -26;
    private static final byte FINAL_INPUT_BYTE = 68;

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        Base16 base16 = new Base16(false);
        byte[] encodedInput = new byte[6];
        encodedInput[DECODE_OFFSET] = FINAL_INPUT_BYTE;

        BaseNCodec.Context context = new BaseNCodec.Context();
        context.ibitWorkArea = INITIAL_WORK_AREA;

        base16.decode(encodedInput, DECODE_OFFSET, DECODE_LENGTH, context);

        assertFalse(base16.isStrictDecoding());
    }
}
