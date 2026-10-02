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

    /**
     * Verifies that encoding bytes that require no percent-encoding returns the original
     * byte array instance unchanged (identity, not a copy).
     *
     * The codec is configured with an empty always-encode set and plusForSpace=false,
     * so null bytes (0x00) — which are valid ASCII and not in the always-encode set —
     * pass through unmodified. The Object-typed encode overload delegates to the
     * byte[] overload and must preserve the same reference when no encoding is needed.
     */
    @Test(timeout = 4000)
    public void test15() throws Throwable {
        // Arrange: 5 null bytes, none of which need percent-encoding
        byte[] inputBytes = new byte[5];

        // A codec with no special always-encode characters and no plus-for-space substitution
        byte[] noAlwaysEncodeChars = new byte[0];
        PercentCodec codec = new PercentCodec(noAlwaysEncodeChars, false);

        // Act: encode via the Object overload
        Object encodedResult = codec.encode((Object) inputBytes);

        // Assert: the result is the exact same array instance (no encoding was necessary)
        assertSame(inputBytes, encodedResult);
        assertNotNull(encodedResult);
    }
}
