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
public class ExtraFieldUtils_ESTest_test02 extends ExtraFieldUtils_ESTest_scaffolding {

    /**
     * Verifies that {@link ExtraFieldUtils#mergeLocalFileDataData(ZipExtraField[])}
     * concatenates the local-file-data encoding of every field in the array.
     *
     * <p>The array mixes several concrete {@link ZipExtraField} implementations,
     * with the same Unicode-path field appearing twice and an
     * {@link UnparseableExtraFieldData} holder placed last. Each regular field
     * contributes a 4-byte header (2-byte id + 2-byte length) plus its own data,
     * so the merged buffer is expected to be exactly 136 bytes long.</p>
     */
    @Test(timeout = 4000)
    public void mergeLocalFileDataData_concatenatesAllFields_returns136Bytes() throws Throwable {
        // The Unicode-path field is referenced twice in the array below.
        UnicodePathExtraField unicodePathField = new UnicodePathExtraField();

        // Eight zero bytes used as the raw Unicode-comment payload.
        byte[] unicodeCommentBytes = new byte[8];

        ZipExtraField[] extraFields = new ZipExtraField[7];
        extraFields[0] = unicodePathField;
        extraFields[1] = new X000A_NTFS();
        extraFields[2] = unicodePathField;
        extraFields[3] = new UnicodeCommentExtraField("Error parsing extra fields for entry: ", unicodeCommentBytes);
        extraFields[4] = new X0015_CertificateIdForFile();
        extraFields[5] = new ResourceAlignmentExtraField((byte) 4, true, (byte) 35);
        extraFields[6] = new UnparseableExtraFieldData();

        byte[] mergedLocalFileData = ExtraFieldUtils.mergeLocalFileDataData(extraFields);

        assertEquals(136, mergedLocalFileData.length);
    }
}
