package org.apache.commons.compress.harmony.pack200;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;

public class CodecEncodingTest_testGetSpeciferForPopulationCodec {

    @Test
    void testGetSpeciferForPopulationCodec() throws IOException, Pack200Exception {
        final PopulationCodec populationCodec = new PopulationCodec(Codec.BYTE1, Codec.CHAR3, Codec.UNSIGNED5);

        final int[] specifiers = CodecEncoding.getSpecifier(populationCodec, null);
        assertTrue(specifiers[0] > 140);
        assertTrue(specifiers[0] < 189);

        final InputStream encodedPopulationCodec = new ByteArrayInputStream(toBandHeaderBytes(specifiers));
        final PopulationCodec decodedPopulationCodec = (PopulationCodec) CodecEncoding.getCodec(specifiers[0], encodedPopulationCodec, null);

        assertEquals(populationCodec.getFavouredCodec(), decodedPopulationCodec.getFavouredCodec());
        assertEquals(populationCodec.getTokenCodec(), decodedPopulationCodec.getTokenCodec());
        assertEquals(populationCodec.getUnfavouredCodec(), decodedPopulationCodec.getUnfavouredCodec());
    }

    private static byte[] toBandHeaderBytes(final int[] specifiers) {
        final byte[] bytes = new byte[specifiers.length - 1];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) specifiers[i + 1];
        }
        return bytes;
    }
}
