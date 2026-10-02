package org.apache.commons.compress.harmony.pack200;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.junit.jupiter.api.Test;

public class CodecEncodingTest_testGetSpeciferForRunCodec {

    /**
     * Takes a specifier array produced by {@link CodecEncoding#getSpecifier}, turns the
     * trailing bytes (indices 1..n-1) into a ByteArrayInputStream, and reconstructs the
     * codec encoded at {@code specifiers[0]}.
     */
    private RunCodec roundTripRunCodec(int[] specifiers, Codec defaultCodec)
            throws IOException, Pack200Exception {
        byte[] headerBytes = new byte[specifiers.length - 1];
        for (int i = 0; i < headerBytes.length; i++) {
            headerBytes[i] = (byte) specifiers[i + 1];
        }
        InputStream in = new ByteArrayInputStream(headerBytes);
        return (RunCodec) CodecEncoding.getCodec(specifiers[0], in, defaultCodec);
    }

    private void assertSpecifierIsRunCodecRange(int[] specifiers) {
        // Per the Pack200 spec, run-codec specifier bytes occupy the range [117, 140]
        assertTrue(specifiers[0] > 116);
        assertTrue(specifiers[0] < 141);
    }

    @Test
    void testGetSpeciferForRunCodec() throws Pack200Exception, IOException {

        // Scenario 1: simple RunCodec with no shared default codec
        RunCodec runCodec = new RunCodec(25, Codec.DELTA5, Codec.BYTE1);
        int[] specifiers = CodecEncoding.getSpecifier(runCodec, null);
        assertSpecifierIsRunCodecRange(specifiers);
        RunCodec runCodec2 = roundTripRunCodec(specifiers, null);
        assertEquals(runCodec.getK(), runCodec2.getK());
        assertEquals(runCodec.getACodec(), runCodec2.getACodec());
        assertEquals(runCodec.getBCodec(), runCodec2.getBCodec());

        // Scenario 2: RunCodec where the A codec equals the default — it is encoded as
        // a back-reference to the default rather than written out in full
        runCodec = new RunCodec(4096, Codec.DELTA5, Codec.BYTE1);
        specifiers = CodecEncoding.getSpecifier(runCodec, Codec.DELTA5);
        assertSpecifierIsRunCodecRange(specifiers);
        runCodec2 = roundTripRunCodec(specifiers, Codec.DELTA5);
        assertEquals(runCodec.getK(), runCodec2.getK());
        assertEquals(runCodec.getACodec(), runCodec2.getACodec());
        assertEquals(runCodec.getBCodec(), runCodec2.getBCodec());

        // Scenario 3: nested RunCodec (B codec is itself a RunCodec) with no default
        runCodec = new RunCodec(64, Codec.SIGNED5, new RunCodec(25, Codec.UDELTA5, Codec.DELTA5));
        specifiers = CodecEncoding.getSpecifier(runCodec, null);
        assertSpecifierIsRunCodecRange(specifiers);
        runCodec2 = roundTripRunCodec(specifiers, null);
        assertEquals(runCodec.getK(), runCodec2.getK());
        assertEquals(runCodec.getACodec(), runCodec2.getACodec());
        RunCodec bCodec  = (RunCodec) runCodec.getBCodec();
        RunCodec bCodec2 = (RunCodec) runCodec2.getBCodec();
        assertEquals(bCodec.getK(), bCodec2.getK());
        assertEquals(bCodec.getACodec(), bCodec2.getACodec());
        assertEquals(bCodec.getBCodec(), bCodec2.getBCodec());

        // Scenario 4: nested RunCodec where the inner A codec equals the default —
        // the inner A codec is omitted from the specifier bytes
        runCodec = new RunCodec(64, Codec.SIGNED5, new RunCodec(25, Codec.UDELTA5, Codec.DELTA5));
        specifiers = CodecEncoding.getSpecifier(runCodec, Codec.UDELTA5);
        assertSpecifierIsRunCodecRange(specifiers);
        runCodec2 = roundTripRunCodec(specifiers, Codec.UDELTA5);
        assertEquals(runCodec.getK(), runCodec2.getK());
        assertEquals(runCodec.getACodec(), runCodec2.getACodec());
        bCodec  = (RunCodec) runCodec.getBCodec();
        bCodec2 = (RunCodec) runCodec2.getBCodec();
        assertEquals(bCodec.getK(), bCodec2.getK());
        assertEquals(bCodec.getACodec(), bCodec2.getACodec());
        assertEquals(bCodec.getBCodec(), bCodec2.getBCodec());
    }
}
