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

    @Test(timeout = 4000)
    public void test02() throws Throwable {
        ZipExtraField[] extraFields = new ZipExtraField[7];

        UnicodePathExtraField unicodePathField = new UnicodePathExtraField();
        extraFields[0] = (ZipExtraField) unicodePathField;

        X000A_NTFS ntfsField = new X000A_NTFS();
        extraFields[1] = (ZipExtraField) ntfsField;

        byte[] commentBytes = new byte[8];
        extraFields[2] = (ZipExtraField) unicodePathField;

        UnicodeCommentExtraField unicodeCommentField =
                new UnicodeCommentExtraField("Error parsing extra fields for entry: ", commentBytes);
        extraFields[3] = (ZipExtraField) unicodeCommentField;

        X0015_CertificateIdForFile certificateIdForFile = new X0015_CertificateIdForFile();
        extraFields[4] = (ZipExtraField) certificateIdForFile;

        ResourceAlignmentExtraField resourceAlignmentField =
                new ResourceAlignmentExtraField((byte) 4, true, (byte) 35);
        extraFields[5] = (ZipExtraField) resourceAlignmentField;

        UnparseableExtraFieldData unparseableData = new UnparseableExtraFieldData();
        extraFields[6] = (ZipExtraField) unparseableData;

        byte[] mergedLocalData = ExtraFieldUtils.mergeLocalFileDataData(extraFields);

        assertEquals(136, mergedLocalData.length);
    }
}
