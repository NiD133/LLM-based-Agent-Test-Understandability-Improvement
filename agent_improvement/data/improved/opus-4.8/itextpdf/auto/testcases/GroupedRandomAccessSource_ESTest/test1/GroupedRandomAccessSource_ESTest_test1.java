package com.itextpdf.text.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class GroupedRandomAccessSource_ESTest_test1 extends GroupedRandomAccessSource_ESTest_scaffolding {

    /**
     * Verifies that reading past the end of a GroupedRandomAccessSource returns -1
     * (end-of-source) while the reported length still reflects the configured window size.
     *
     * Setup: a single 807-byte window (declared length 807) layered over a 6-byte
     * backing array, wrapped in a GroupedRandomAccessSource. The grouped source therefore
     * advertises a length of 807. The read targets a position and buffer offset that lie
     * outside the available data, so nothing is read and -1 is returned.
     */
    @Test(timeout = 4000)
    public void getBeyondAvailableDataReturnsEndOfSource() throws Throwable {
        // Backing storage and the buffer the read will (attempt to) fill.
        byte[] buffer = new byte[6];

        // Build the window: offset 807, length 807 over the 6-byte backing array.
        ArrayRandomAccessSource backingSource = new ArrayRandomAccessSource(buffer);
        final long windowOffset = 807L;
        final long windowLength = 807L;
        WindowRandomAccessSource window =
                new WindowRandomAccessSource(backingSource, windowOffset, windowLength);

        // Group containing the single window source.
        RandomAccessSource[] sources = new RandomAccessSource[] { window };
        GroupedRandomAccessSource groupedSource = new GroupedRandomAccessSource(sources);

        // Read starting at position 196, writing into the buffer at offset 1318 for 1318 bytes:
        // all of these lie beyond the available data, so the read yields end-of-source.
        final long readPosition = 196L;
        final int bufferOffset = 1318;
        final int requestedLength = 1318;
        int bytesRead = groupedSource.get(readPosition, buffer, bufferOffset, requestedLength);

        assertEquals("Reading beyond available data should signal end-of-source", -1, bytesRead);
        assertEquals("Grouped length should equal the window's declared length",
                windowLength, groupedSource.length());
    }
}
