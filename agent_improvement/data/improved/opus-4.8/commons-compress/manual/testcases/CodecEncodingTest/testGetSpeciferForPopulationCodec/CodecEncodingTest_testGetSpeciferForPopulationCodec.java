package org.apache.commons.compress.harmony.pack200;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;

public class CodecEncodingTest_testGetSpeciferForPopulationCodec {

    /**
     * A {@link PopulationCodec} is not one of the canonical encodings, so {@link CodecEncoding#getSpecifier}
     * must emit a multi-byte specifier: a leading meta-encoding byte (in the population-codec range 141..188)
     * followed by the bytes describing its three sub-codecs. This test verifies that round-tripping such a
     * codec through {@code getSpecifier} and back through {@link CodecEncoding#getCodec} reproduces the
     * original favoured, token and unfavoured codecs.
     */
    @Test
    void testGetSpeciferForPopulationCodec() throws IOException, Pack200Exception {
        final PopulationCodec originalCodec = new PopulationCodec(Codec.BYTE1, Codec.CHAR3, Codec.UNSIGNED5);

        // specifier[0] is the meta-encoding byte; specifier[1..] are the extra header bytes.
        final int[] specifier = CodecEncoding.getSpecifier(originalCodec, null);

        // The leading byte must fall within the range reserved for population codecs (141..188).
        final int metaEncodingByte = specifier[0];
        assertTrue(metaEncodingByte > 140);
        assertTrue(metaEncodingByte < 189);

        // Feed the trailing header bytes back through getCodec to reconstruct the codec.
        final byte[] headerBytes = new byte[specifier.length - 1];
        for (int i = 0; i < headerBytes.length; i++) {
            headerBytes[i] = (byte) specifier[i + 1];
        }
        final InputStream headerStream = new ByteArrayInputStream(headerBytes);
        final PopulationCodec reconstructedCodec =
                (PopulationCodec) CodecEncoding.getCodec(metaEncodingByte, headerStream, null);

        // The reconstructed codec must match the original in all three sub-codecs.
        assertEquals(originalCodec.getFavouredCodec(), reconstructedCodec.getFavouredCodec());
        assertEquals(originalCodec.getTokenCodec(), reconstructedCodec.getTokenCodec());
        assertEquals(originalCodec.getUnfavouredCodec(), reconstructedCodec.getUnfavouredCodec());
    }
}
