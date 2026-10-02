package org.apache.commons.compress.harmony.pack200;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;

public class CodecEncodingTest_testGetSpeciferForPopulationCodec {

    // Per the Pack200 spec (section 6.7.3), population codec specifier bytes fall in [141, 188].
    private static final int POPULATION_CODEC_SPECIFIER_MIN = 141;
    private static final int POPULATION_CODEC_SPECIFIER_MAX = 188;

    /**
     * Verifies that a PopulationCodec round-trips through getSpecifier / getCodec:
     * the specifier produced for a codec must decode back to an equivalent codec
     * with the same favoured, token, and unfavoured sub-codecs.
     */
    @Test
    void testGetSpeciferForPopulationCodec() throws IOException, Pack200Exception {
        // Build a population codec with three distinct sub-codecs.
        final PopulationCodec original = new PopulationCodec(Codec.BYTE1, Codec.CHAR3, Codec.UNSIGNED5);

        // Encode the codec as a specifier: specifiers[0] is the leading byte,
        // specifiers[1..] are the additional band-header bytes.
        final int[] specifiers = CodecEncoding.getSpecifier(original, null);

        // The leading byte must identify a population codec (141–188).
        assertTrue(specifiers[0] >= POPULATION_CODEC_SPECIFIER_MIN,
                "Leading specifier byte should be >= " + POPULATION_CODEC_SPECIFIER_MIN);
        assertTrue(specifiers[0] <= POPULATION_CODEC_SPECIFIER_MAX,
                "Leading specifier byte should be <= " + POPULATION_CODEC_SPECIFIER_MAX);

        // Pack the additional specifier bytes into the band-header stream.
        final byte[] bandHeaderBytes = new byte[specifiers.length - 1];
        for (int i = 0; i < bandHeaderBytes.length; i++) {
            bandHeaderBytes[i] = (byte) specifiers[i + 1];
        }
        final InputStream bandHeaderStream = new ByteArrayInputStream(bandHeaderBytes);

        // Decode the specifier back into a codec and verify all three sub-codecs match.
        final PopulationCodec decoded =
                (PopulationCodec) CodecEncoding.getCodec(specifiers[0], bandHeaderStream, null);

        assertEquals(original.getFavouredCodec(), decoded.getFavouredCodec(),
                "Favoured codec should survive the specifier round-trip");
        assertEquals(original.getTokenCodec(), decoded.getTokenCodec(),
                "Token codec should survive the specifier round-trip");
        assertEquals(original.getUnfavouredCodec(), decoded.getUnfavouredCodec(),
                "Unfavoured codec should survive the specifier round-trip");
    }
}
