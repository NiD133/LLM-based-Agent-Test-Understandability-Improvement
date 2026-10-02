package org.apache.commons.codec.net;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PercentCodec_ESTest_test15 extends PercentCodec_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test15() throws Throwable {
        byte[] bytesWithoutCharactersToEncode = new byte[5];
        byte[] noAdditionalAlwaysEncodeChars = new byte[0];
        PercentCodec codecWithoutConfiguredUnsafeChars = new PercentCodec(noAdditionalAlwaysEncodeChars, false);

        Object encodedBytes = codecWithoutConfiguredUnsafeChars.encode((Object) bytesWithoutCharactersToEncode);

        assertSame(bytesWithoutCharactersToEncode, encodedBytes);
        assertNotNull(encodedBytes);
    }
}
