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
public class Base16_ESTest_test01 extends Base16_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        // Create a Base16 codec using the upper-case (non-lower-case) alphabet
        Base16 base16 = new Base16(false);

        // Prepare a 6-byte buffer; place 'D' (ASCII 68, a valid upper-case hex digit) at the last position
        byte[] inputData = new byte[6];
        inputData[5] = (byte) 'D';

        BaseNCodec.Context context = new BaseNCodec.Context();

        // Decode from offset 5 with a length that exceeds the remaining array bounds (1 byte available),
        // so only the single byte 'D' at index 5 is processed and held as a partial hex pair in context
        base16.decode(inputData, 5, 1767, context);

        // The PEM chunk size is a well-known constant defined as 64 characters per line
        assertEquals(64, BaseNCodec.PEM_CHUNK_SIZE);
    }
}
