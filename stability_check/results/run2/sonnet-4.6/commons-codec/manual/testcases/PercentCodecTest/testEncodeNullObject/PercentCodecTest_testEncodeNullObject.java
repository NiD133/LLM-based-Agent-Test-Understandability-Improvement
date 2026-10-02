package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class PercentCodecTest_testEncodeNullObject {

    /**
     * Verifies that encoding a null Object input returns null rather than throwing an exception.
     * The encode(Object) method is the Encoder interface entry-point; callers may pass null
     * to signal "nothing to encode," and the codec should propagate that null cleanly.
     */
    @Test
    void testEncodeNullObject() throws Exception {
        PercentCodec codec = new PercentCodec();

        Object result = codec.encode((Object) null);

        assertNull(result, "encode(null) should return null, not throw");
    }
}
