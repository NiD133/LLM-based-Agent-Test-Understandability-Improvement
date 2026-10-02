package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class PercentCodecTest_testSafeCharEncodeDecodeObject {

    @Test
    void testSafeCharEncodeDecodeObject() throws Exception {
        final PercentCodec percentCodec = new PercentCodec(null, true);
        final String safeCharacters = "abc123_-.*";
        final byte[] safeCharacterBytes = safeCharacters.getBytes(StandardCharsets.UTF_8);

        final Object encodedObject = percentCodec.encode((Object) safeCharacterBytes);
        final String encodedText = new String((byte[]) encodedObject, StandardCharsets.UTF_8);

        final Object decodedObject = percentCodec.decode(encodedObject);
        final String decodedText = new String((byte[]) decodedObject, StandardCharsets.UTF_8);

        assertEquals(safeCharacters, encodedText, "Basic PercentCodec safe char encoding test");
        assertEquals(safeCharacters, decodedText, "Basic PercentCodec safe char decoding test");
    }
}
