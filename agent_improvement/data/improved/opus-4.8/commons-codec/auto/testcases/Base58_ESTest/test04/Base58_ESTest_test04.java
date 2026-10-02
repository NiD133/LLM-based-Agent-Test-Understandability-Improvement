package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Base58_ESTest_test04 extends Base58_ESTest_scaffolding {

    /**
     * A negative length passed to {@link Base58#decode} signals EOF, which flips the
     * context into its end-of-file state. Once EOF has been signaled, any further
     * decode call returns immediately without touching the data.
     *
     * <p>Neither call changes the codec's decoding policy, so the instance keeps its
     * default of non-strict decoding.</p>
     */
    @Test(timeout = 4000)
    public void decodeAfterEofIsIgnoredAndDecodingStaysNonStrict() throws Throwable {
        Base58 base58 = new Base58();
        byte[] emptyData = new byte[5];
        BaseNCodec.Context context = new BaseNCodec.Context();

        // Negative length (-98) signals EOF and puts the context into its EOF state.
        final int offset = 95;
        final int eofSignalLength = -98;
        base58.decode(emptyData, offset, eofSignalLength, context);

        // EOF was already signaled, so this positive-length call is a no-op.
        final int secondOffset = 88;
        final int secondLength = 71;
        base58.decode(emptyData, secondOffset, secondLength, context);

        assertFalse(base58.isStrictDecoding());
    }
}
