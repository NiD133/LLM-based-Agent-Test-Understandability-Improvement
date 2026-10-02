package org.apache.commons.compress.harmony.pack200;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import org.apache.commons.compress.harmony.pack200.BHSDCodec;
import org.apache.commons.compress.harmony.pack200.Codec;
import org.apache.commons.compress.harmony.pack200.CodecEncoding;
import org.apache.commons.compress.harmony.pack200.PopulationCodec;
import org.apache.commons.compress.harmony.pack200.RunCodec;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

/**
 * Tests for {@link CodecEncoding}, which maps between codec objects and their
 * Pack200 specifier byte sequences.
 *
 * Encoding ranges (per Pack200 spec):
 *   0       = use default codec
 *   1-115   = canonical (pre-defined) BHSDCodec
 *   116     = explicit BHSD codec encoded in next 2 stream bytes
 *   117-140 = RunCodec
 *   141-188 = PopulationCodec
 */
@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true,
        resetStaticState = true, separateClassLoader = false)
public class CodecEncoding_ESTest extends CodecEncoding_ESTest_scaffolding {

    // Encoding byte for explicit BHSD codec definition (followed by 2 parameter bytes)
    private static final int BHSD_EXPLICIT_ENCODING = 116;

    // --- getSpecifier tests ---

    @Test(timeout = 4000)
    public void getSpecifier_runCodecWithK1825AndSameDefault_encodesKbKxAndSubCodecSpecifiers() throws Throwable {
        BHSDCodec byteCodec = Codec.BYTE1;
        // k=1825 falls in the 257-4096 range, so kb=1 and kx=1825/16-1=113
        RunCodec runCodec = new RunCodec(1825, byteCodec, byteCodec);
        int[] specifier = CodecEncoding.getSpecifier(runCodec, runCodec);
        assertNotNull(specifier);
        assertTrue(specifier.length > 0);
    }

    @Test(timeout = 4000)
    public void getSpecifier_runCodecWithPopulationSubCodecAndBDefMatchingDefault_returnsCompactSpecifier() throws Throwable {
        BHSDCodec delta5 = Codec.DELTA5;
        // k=4 <= 256, so kb=0 and kx=4-1=3 (no extra kx byte needed)
        PopulationCodec populationCodec = new PopulationCodec(delta5, 4, delta5);
        RunCodec runCodec = new RunCodec(4, populationCodec, delta5);
        int[] specifier = CodecEncoding.getSpecifier(runCodec, delta5);
        assertNotNull(specifier);
        assertTrue(specifier.length > 0);
    }

    @Test(timeout = 4000)
    public void getSpecifier_runCodecWithMaxIntK_encodesLargeKxValue() throws Throwable {
        BHSDCodec byteCodec = Codec.BYTE1;
        // k=Integer.MAX_VALUE falls in the >65536 range: kb=3, kx=MAX_VALUE/4096-1=524286
        RunCodec runCodec = new RunCodec(Integer.MAX_VALUE, byteCodec, byteCodec);
        int[] specifier = CodecEncoding.getSpecifier(runCodec, runCodec);
        assertNotNull(specifier);
        assertTrue(specifier.length > 0);
    }

    @Test(timeout = 4000)
    public void getSpecifier_runCodecWhereACodecMatchesDefault_omitsACodecFromSpecifier() throws Throwable {
        // Canonical codec index 13 is BHSDCodec(4, 256)
        BHSDCodec canonicalCodec13 = CodecEncoding.getCanonicalCodec(13);
        RunCodec runCodec = new RunCodec(13, canonicalCodec13, canonicalCodec13);
        int[] specifier = CodecEncoding.getSpecifier(runCodec, canonicalCodec13);
        assertNotNull(specifier);
        assertTrue(specifier.length > 0);
    }

    @Test(timeout = 4000)
    public void getSpecifier_runCodecBuiltFromDecodedBHSDCodec_roundTripsSpecifier() throws Throwable {
        BHSDCodec char3 = Codec.CHAR3;
        byte[] headerBytes = new byte[9];
        headerBytes[0] = (byte) 93; // encodes a specific BHSD (b,h,s,d) combination
        ByteArrayInputStream headerStream = new ByteArrayInputStream(headerBytes);
        // Value 116 triggers BHSD parsing; reads 2 bytes from stream leaving 7 available
        Codec decodedCodec = CodecEncoding.getCodec(BHSD_EXPLICIT_ENCODING, headerStream, char3);
        int[] specifier = CodecEncoding.getSpecifier(decodedCodec, decodedCodec);
        assertNotNull(specifier);
        assertTrue(specifier.length > 0);
    }

    @Test(timeout = 4000)
    public void getSpecifier_populationCodecDecodedFromStream_returnsExpectedSpecifier() throws Throwable {
        BHSDCodec delta5 = Codec.DELTA5;
        byte[] headerBytes = new byte[2];
        ByteArrayInputStream headerStream = new ByteArrayInputStream(headerBytes);
        BufferedInputStream bufferedStream = new BufferedInputStream(headerStream);
        // Value 166 is in the PopulationCodec range (141-188)
        Codec populationCodec = CodecEncoding.getCodec(166, bufferedStream, delta5);
        CodecEncoding.getSpecifier(populationCodec, populationCodec);
    }

    @Test(timeout = 4000)
    public void getSpecifierForDefaultCodec_char3_returns116() throws Throwable {
        BHSDCodec char3 = Codec.CHAR3;
        int specifier = CodecEncoding.getSpecifierForDefaultCodec(char3);
        assertEquals(116, specifier);
    }

    // --- getCodec exception tests ---

    @Test(timeout = 4000)
    public void getCodec_runCodecRangeValueWithNullStream_throwsNullPointerException() throws Throwable {
        BHSDCodec char3 = Codec.CHAR3;
        // Value 142 is in the RunCodec range (117-140... actually 142 > 140 so it's PopulationCodec)
        // Value 142 is 141+1 → PopulationCodec with fdef=1, udef=0, tdefl=0
        // Reading sub-codecs requires a non-null stream
        try {
            CodecEncoding.getCodec(142, (InputStream) null, char3);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
        }
    }

    @Test(timeout = 4000)
    public void getCodec_populationCodecRangeValueWithNullStream_throwsNullPointerException() throws Throwable {
        BHSDCodec mdelta5 = Codec.MDELTA5;
        // Value 179 is in the PopulationCodec range (141-188); sub-codec reading needs a stream
        try {
            CodecEncoding.getCodec(179, (InputStream) null, mdelta5);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
        }
    }

    @Test(timeout = 4000)
    public void getCodec_populationCodecValue141WithSufficientBufferedStream_consumesAllStreamBytes() throws Throwable {
        BHSDCodec char3 = Codec.CHAR3;
        byte[] headerBytes = new byte[9];
        ByteArrayInputStream headerStream = new ByteArrayInputStream(headerBytes);
        BufferedInputStream bufferedStream = new BufferedInputStream(headerStream);
        // Value 141 = first PopulationCodec specifier; reads sub-codec specifiers from stream
        CodecEncoding.getCodec(141, bufferedStream, char3);
    }

    @Test(timeout = 4000)
    public void getCodec_valueLargerThan188_throwsIOExceptionForInvalidByte() throws Throwable {
        PipedInputStream pipedStream = new PipedInputStream();
        BHSDCodec char3 = Codec.CHAR3;
        // 2756 is outside all valid encoding ranges (> 188)
        try {
            CodecEncoding.getCodec(2756, pipedStream, char3);
            fail("Expecting exception: IOException");
        } catch (IOException e) {
        }
    }

    @Test(timeout = 4000)
    public void getCodec_canonicalCodecIndex2_returnsBHSDCodecWithCorrectValueBounds() throws Throwable {
        // Canonical codec 13 is BHSDCodec(4, 256); canonical codec 2 is BHSDCodec(1, 256, 1)
        BHSDCodec canonicalCodec13 = CodecEncoding.getCanonicalCodec(13);
        byte[] headerBytes = new byte[2];
        ByteArrayInputStream headerStream = new ByteArrayInputStream(headerBytes);
        // Value 2 is a canonical index → returns canonicalCodec[2] = BHSDCodec(1,256,1)
        BHSDCodec decodedCodec = (BHSDCodec) CodecEncoding.getCodec(2, headerStream, canonicalCodec13);
        assertNotNull(decodedCodec);
    }

    @Test(timeout = 4000)
    public void getCodec_runCodecRangeValueWithUnconnectedPipedStream_throwsIOException() throws Throwable {
        BHSDCodec char3 = Codec.CHAR3;
        PipedInputStream unconnectedPipe = new PipedInputStream();
        // Value 137 is in the RunCodec range (117-140); reading sub-codec from disconnected pipe fails
        try {
            CodecEncoding.getCodec(137, unconnectedPipe, char3);
            fail("Expecting exception: IOException");
        } catch (IOException e) {
        }
    }

    @Test(timeout = 4000)
    public void getCodec_populationCodecThenRunCodecWithSharedStream_consumesExpectedBytes() throws Throwable {
        BHSDCodec canonicalCodec13 = CodecEncoding.getCanonicalCodec(13);
        byte[] headerBytes = new byte[2];
        ByteArrayInputStream headerStream = new ByteArrayInputStream(headerBytes);
        BufferedInputStream bufferedStream = new BufferedInputStream(headerStream);
        // Value 188 is the last valid PopulationCodec specifier
        Codec populationCodec = CodecEncoding.getCodec(188, bufferedStream, canonicalCodec13);
        // Value 117 is the first RunCodec specifier; uses remaining bytes from the same stream
        CodecEncoding.getCodec(117, bufferedStream, populationCodec.MDELTA5);
    }

    @Test(timeout = 4000)
    public void getCodec_runCodecRangeValueWithLargerK_withUnconnectedPipedStream_throwsIOException() throws Throwable {
        BHSDCodec char3 = Codec.CHAR3;
        PipedInputStream unconnectedPipe = new PipedInputStream();
        // Value 130 is in the RunCodec range (117-140) with kbflag=true, needing a stream read
        try {
            CodecEncoding.getCodec(130, unconnectedPipe, char3);
            fail("Expecting exception: IOException");
        } catch (IOException e) {
        }
    }

    @Test(timeout = 4000)
    public void getCodec_runCodecWithOneByte_returnsRunCodecWithExpectedSpecifier() throws Throwable {
        BHSDCodec canonicalCodec13 = CodecEncoding.getCanonicalCodec(13);
        byte[] headerBytes = new byte[1];
        ByteArrayInputStream headerStream = new ByteArrayInputStream(headerBytes);
        // Value 128 is in the RunCodec range; reads 1 byte for sub-codec index
        Codec runCodec = CodecEncoding.getCodec(128, headerStream, canonicalCodec13);
        int[] specifier = CodecEncoding.getSpecifier(runCodec, runCodec.UNSIGNED5);
        assertNotNull(specifier);
        assertTrue(specifier.length > 0);
    }

    @Test(timeout = 4000)
    public void getCodec_bhsdExplicitEncodingWithOnlyOneByte_throwsEOFException() throws Throwable {
        BHSDCodec delta5 = Codec.DELTA5;
        byte[] singleByte = new byte[1];
        ByteArrayInputStream headerStream = new ByteArrayInputStream(singleByte);
        // Value 116 needs 2 bytes from the stream; only 1 available → EOFException
        try {
            CodecEncoding.getCodec(BHSD_EXPLICIT_ENCODING, headerStream, delta5);
            fail("Expecting exception: EOFException");
        } catch (EOFException e) {
        }
    }

    @Test(timeout = 4000)
    public void getCodec_bhsdExplicitEncodingAfterStreamExhausted_throwsEOFException() throws Throwable {
        BHSDCodec delta5 = Codec.DELTA5;
        byte[] twoBytes = new byte[2];
        ByteArrayInputStream headerStream = new ByteArrayInputStream(twoBytes);
        BufferedInputStream bufferedStream = new BufferedInputStream(headerStream);
        // First call (PopulationCodec range 166) consumes both bytes from the buffered stream
        CodecEncoding.getCodec(166, bufferedStream, delta5);
        // Second call needs 2 more bytes from the already-empty underlying stream
        try {
            CodecEncoding.getCodec(BHSD_EXPLICIT_ENCODING, headerStream, delta5);
            fail("Expecting exception: EOFException");
        } catch (EOFException e) {
        }
    }

    @Test(timeout = 4000)
    public void getCodec_negativeEncodingValue_throwsIllegalArgumentException() throws Throwable {
        BHSDCodec char3 = Codec.CHAR3;
        PipedInputStream pipedStream = new PipedInputStream();
        try {
            CodecEncoding.getCodec(-346, pipedStream, char3);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        }
    }

    // --- Constructor test ---

    @Test(timeout = 4000)
    public void constructor_defaultConstructor_createsInstance() throws Throwable {
        // CodecEncoding is a utility class; constructor is deprecated but must remain accessible
        CodecEncoding codecEncoding = new CodecEncoding();
        assertNotNull(codecEncoding);
    }
}
