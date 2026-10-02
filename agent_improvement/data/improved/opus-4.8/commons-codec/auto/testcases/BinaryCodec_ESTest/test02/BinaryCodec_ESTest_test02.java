package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BinaryCodec_ESTest_test02 extends BinaryCodec_ESTest_scaffolding {

    /**
     * Verifies that decoding then encoding an empty input is a stable round-trip.
     *
     * <p>Decoding the empty String yields the shared empty byte array. Encoding that
     * empty byte array yields an empty char array, and decoding that empty char array
     * yields the empty byte array again. Because {@link BinaryCodec} returns the same
     * cached empty byte[] constant for all empty inputs, the first and final results
     * are not merely equal but the very same object instance.</p>
     */
    @Test(timeout = 4000)
    public void decodeEncodeRoundTripOfEmptyInputReturnsSameEmptyByteArray() throws Throwable {
        BinaryCodec binaryCodec = new BinaryCodec();

        Object decodedEmptyString = binaryCodec.decode((Object) "");
        Object encodedAscii = binaryCodec.encode(decodedEmptyString);
        Object reDecodedResult = binaryCodec.decode(encodedAscii);

        assertSame(reDecodedResult, decodedEmptyString);
    }
}
