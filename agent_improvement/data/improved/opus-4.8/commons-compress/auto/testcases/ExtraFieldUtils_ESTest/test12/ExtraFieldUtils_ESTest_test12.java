package org.apache.commons.compress.archivers.zip;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.zip.ZipException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ExtraFieldUtils_ESTest_test12 extends ExtraFieldUtils_ESTest_scaffolding {

    /**
     * Verifies that parsing extra-field bytes whose second entry declares a length
     * that overruns the buffer keeps that trailing data (using the READ behavior),
     * and that merging the parsed fields back reproduces the original byte count.
     *
     * <p>The 13-byte buffer is laid out as two extra-field records:</p>
     * <ul>
     *   <li>bytes 0-3: header id 0x0000 with declared length 0 -&gt; one parseable
     *       (unrecognized) field with no payload.</li>
     *   <li>bytes 4-12: header id 0x0000 with a declared length of 0xF800 (set via
     *       byte 7 = -8). That length far exceeds the 9 remaining bytes, so the
     *       record is unparseable; READ tells the parser to keep the remaining 9
     *       bytes as an {@code UnparseableExtraFieldData} field.</li>
     * </ul>
     */
    @Test(timeout = 4000)
    public void parseWithReadKeepsOverlongTrailingFieldAndMergePreservesLength() throws Throwable {
        // 13-byte local-file-data buffer; byte 7 makes the second record claim an
        // oversized length so it cannot be parsed normally.
        byte[] localFileData = new byte[13];
        localFileData[7] = (byte) -8;

        // READ: keep the unparseable trailing bytes instead of throwing or skipping.
        ExtraFieldUtils.UnparseableExtraField onUnparseable = ExtraFieldUtils.UnparseableExtraField.READ;

        ZipExtraField[] parsedFields = ExtraFieldUtils.parse(localFileData, true, onUnparseable);

        // One regular (unrecognized) field plus one UnparseableExtraFieldData holder.
        assertEquals(2, parsedFields.length);

        byte[] mergedData = ExtraFieldUtils.mergeLocalFileDataData(parsedFields);

        // Merging reproduces the original 13 bytes: 4-byte header of the first field
        // plus the 9 preserved trailing bytes.
        assertEquals(13, mergedData.length);
    }
}
