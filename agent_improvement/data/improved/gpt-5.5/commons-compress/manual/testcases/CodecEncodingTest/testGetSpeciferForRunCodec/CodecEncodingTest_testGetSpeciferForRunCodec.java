package org.apache.commons.compress.harmony.pack200;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;

public class CodecEncodingTest_testGetSpeciferForRunCodec {

    @Test
    void testGetSpeciferForRunCodec() throws Pack200Exception, IOException {
        RunCodec runCodec = new RunCodec(25, Codec.DELTA5, Codec.BYTE1);
        assertRunCodecRoundTrips(runCodec, null);

        // One codec is the same as the default.
        runCodec = new RunCodec(4096, Codec.DELTA5, Codec.BYTE1);
        assertRunCodecRoundTrips(runCodec, Codec.DELTA5);

        // Nested run codecs.
        runCodec = new RunCodec(64, Codec.SIGNED5, new RunCodec(25, Codec.UDELTA5, Codec.DELTA5));
        assertNestedRunCodecRoundTrips(runCodec, null);

        // Nested with one the same as the default.
        runCodec = new RunCodec(64, Codec.SIGNED5, new RunCodec(25, Codec.UDELTA5, Codec.DELTA5));
        assertNestedRunCodecRoundTrips(runCodec, Codec.UDELTA5);
    }

    private static void assertRunCodecRoundTrips(final RunCodec runCodec, final Codec defaultCodec) throws IOException, Pack200Exception {
        final RunCodec decodedRunCodec = decodeRunCodec(runCodec, defaultCodec);

        assertEquals(runCodec.getK(), decodedRunCodec.getK());
        assertEquals(runCodec.getACodec(), decodedRunCodec.getACodec());
        assertEquals(runCodec.getBCodec(), decodedRunCodec.getBCodec());
    }

    private static void assertNestedRunCodecRoundTrips(final RunCodec runCodec, final Codec defaultCodec) throws IOException, Pack200Exception {
        final RunCodec decodedRunCodec = decodeRunCodec(runCodec, defaultCodec);

        assertEquals(runCodec.getK(), decodedRunCodec.getK());
        assertEquals(runCodec.getACodec(), decodedRunCodec.getACodec());

        final RunCodec nestedRunCodec = (RunCodec) runCodec.getBCodec();
        final RunCodec decodedNestedRunCodec = (RunCodec) decodedRunCodec.getBCodec();
        assertEquals(nestedRunCodec.getK(), decodedNestedRunCodec.getK());
        assertEquals(nestedRunCodec.getACodec(), decodedNestedRunCodec.getACodec());
        assertEquals(nestedRunCodec.getBCodec(), decodedNestedRunCodec.getBCodec());
    }

    private static RunCodec decodeRunCodec(final RunCodec runCodec, final Codec defaultCodec) throws IOException, Pack200Exception {
        final int[] specifiers = CodecEncoding.getSpecifier(runCodec, defaultCodec);
        assertRunCodecSpecifier(specifiers[0]);

        final InputStream in = new ByteArrayInputStream(toBandHeaderBytes(specifiers));
        return (RunCodec) CodecEncoding.getCodec(specifiers[0], in, defaultCodec);
    }

    private static void assertRunCodecSpecifier(final int specifier) {
        assertTrue(specifier > 116);
        assertTrue(specifier < 141);
    }

    private static byte[] toBandHeaderBytes(final int[] specifiers) {
        final byte[] bytes = new byte[specifiers.length - 1];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) specifiers[i + 1];
        }
        return bytes;
    }
}
