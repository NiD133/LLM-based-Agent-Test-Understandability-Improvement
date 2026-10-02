package org.apache.commons.compress.harmony.pack200;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;

public class CodecEncodingTest_testGetSpeciferForRunCodec {

    /**
     * Round-trips a {@link RunCodec} through {@link CodecEncoding}: encodes it to a specifier array,
     * then decodes that array back into a codec.
     * <p>
     * The first entry of the specifier array is the encoding byte; for a RunCodec the spec reserves the
     * range 117..140, which this method asserts. The remaining entries are the band-header bytes that
     * {@link CodecEncoding#getCodec} reads from its input stream.
     *
     * @param original       the codec to encode.
     * @param defaultForBand the band default passed to both encode and decode.
     * @return the codec decoded back from {@code original}'s specifier.
     */
    private static RunCodec encodeThenDecode(final RunCodec original, final Codec defaultForBand)
            throws Pack200Exception, IOException {
        final int[] specifiers = CodecEncoding.getSpecifier(original, defaultForBand);

        final int encodingByte = specifiers[0];
        assertTrue(encodingByte > 116, "encoding byte should be in the RunCodec range");
        assertTrue(encodingByte < 141, "encoding byte should be in the RunCodec range");

        final byte[] bandHeaderBytes = new byte[specifiers.length - 1];
        for (int i = 0; i < bandHeaderBytes.length; i++) {
            bandHeaderBytes[i] = (byte) specifiers[i + 1];
        }

        final InputStream in = new ByteArrayInputStream(bandHeaderBytes);
        return (RunCodec) CodecEncoding.getCodec(encodingByte, in, defaultForBand);
    }

    private static void assertSameRunCodec(final RunCodec expected, final RunCodec actual) {
        assertEquals(expected.getK(), actual.getK());
        assertEquals(expected.getACodec(), actual.getACodec());
        assertEquals(expected.getBCodec(), actual.getBCodec());
    }

    @Test
    void testGetSpeciferForRunCodec() throws Pack200Exception, IOException {
        // Simple run codec, no band default.
        final RunCodec simple = new RunCodec(25, Codec.DELTA5, Codec.BYTE1);
        assertSameRunCodec(simple, encodeThenDecode(simple, null));

        // One of the inner codecs matches the band default, so it is encoded as "default".
        final RunCodec withDefault = new RunCodec(4096, Codec.DELTA5, Codec.BYTE1);
        assertSameRunCodec(withDefault, encodeThenDecode(withDefault, Codec.DELTA5));

        // Nested run codec (the B codec is itself a RunCodec), no band default.
        final RunCodec nested = new RunCodec(64, Codec.SIGNED5, new RunCodec(25, Codec.UDELTA5, Codec.DELTA5));
        RunCodec decoded = encodeThenDecode(nested, null);
        assertEquals(nested.getK(), decoded.getK());
        assertEquals(nested.getACodec(), decoded.getACodec());
        assertSameRunCodec((RunCodec) nested.getBCodec(), (RunCodec) decoded.getBCodec());

        // Nested run codec where an inner codec matches the band default.
        final RunCodec nestedWithDefault = new RunCodec(64, Codec.SIGNED5, new RunCodec(25, Codec.UDELTA5, Codec.DELTA5));
        decoded = encodeThenDecode(nestedWithDefault, Codec.UDELTA5);
        assertEquals(nestedWithDefault.getK(), decoded.getK());
        assertEquals(nestedWithDefault.getACodec(), decoded.getACodec());
        assertSameRunCodec((RunCodec) nestedWithDefault.getBCodec(), (RunCodec) decoded.getBCodec());
    }
}
