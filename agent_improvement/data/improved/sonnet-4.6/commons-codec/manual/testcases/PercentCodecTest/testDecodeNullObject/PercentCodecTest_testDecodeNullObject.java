package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PercentCodecTest_testDecodeNullObject {

    @Test
    @DisplayName("decode(Object) returns null when passed a null Object")
    void testDecodeNullObject() throws DecoderException {
        final PercentCodec percentCodec = new PercentCodec();

        // Cast to Object to invoke the Object overload, not the byte[] overload.
        final Object result = percentCodec.decode((Object) null);

        assertNull(result);
    }
}
