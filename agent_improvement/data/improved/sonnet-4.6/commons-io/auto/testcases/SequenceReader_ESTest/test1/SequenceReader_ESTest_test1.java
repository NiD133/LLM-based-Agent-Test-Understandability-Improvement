package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.PipedReader;
import java.io.Reader;
import java.io.StringReader;
import java.nio.CharBuffer;
import java.nio.ReadOnlyBufferException;
import java.util.LinkedHashSet;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SequenceReader_ESTest_test1 extends SequenceReader_ESTest_scaffolding {

    /**
     * Reading into a CharBuffer with no remaining capacity from an empty SequenceReader
     * should return 0 (zero bytes read), not EOF (-1).
     *
     * The CharBuffer is created from a single-char array and then flipped while at
     * position 0, resulting in limit=0 and remaining()=0 — no space to write into.
     * The SequenceReader has no underlying readers, so there is nothing to read.
     * Because the request size is zero, read() returns 0 rather than EOF.
     */
    @Test(timeout = 4000)
    public void test_readIntoFullyConsumedBuffer_fromEmptySequenceReader_returnsZero() throws Throwable {
        // An empty collection of readers — the SequenceReader will yield no characters
        LinkedHashSet<StringReader> noReaders = new LinkedHashSet<StringReader>();

        // Build a CharBuffer backed by a 1-char array, then flip it so remaining() == 0
        char[] singleCharArray = new char[1];
        CharBuffer zeroCapacityBuffer = CharBuffer.wrap(singleCharArray);
        zeroCapacityBuffer.flip(); // position=0, limit=0 → no space remaining

        SequenceReader emptySequenceReader = new SequenceReader(noReaders);

        // Reading into a zero-capacity buffer requests 0 characters, so the result is 0
        int charsRead = emptySequenceReader.read(zeroCapacityBuffer);
        assertEquals(0, charsRead);
    }
}
