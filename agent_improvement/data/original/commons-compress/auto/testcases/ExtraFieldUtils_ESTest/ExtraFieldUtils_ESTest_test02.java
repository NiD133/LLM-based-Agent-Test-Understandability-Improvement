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
        ZipExtraField[] zipExtraFieldArray0 = new ZipExtraField[7];
        UnicodePathExtraField unicodePathExtraField0 = new UnicodePathExtraField();
        zipExtraFieldArray0[0] = (ZipExtraField) unicodePathExtraField0;
        X000A_NTFS x000A_NTFS0 = new X000A_NTFS();
        zipExtraFieldArray0[1] = (ZipExtraField) x000A_NTFS0;
        byte[] byteArray0 = new byte[8];
        zipExtraFieldArray0[2] = (ZipExtraField) unicodePathExtraField0;
        UnicodeCommentExtraField unicodeCommentExtraField0 = new UnicodeCommentExtraField("Error parsing extra fields for entry: ", byteArray0);
        zipExtraFieldArray0[3] = (ZipExtraField) unicodeCommentExtraField0;
        X0015_CertificateIdForFile x0015_CertificateIdForFile0 = new X0015_CertificateIdForFile();
        zipExtraFieldArray0[4] = (ZipExtraField) x0015_CertificateIdForFile0;
        ResourceAlignmentExtraField resourceAlignmentExtraField0 = new ResourceAlignmentExtraField((byte) 4, true, (byte) 35);
        zipExtraFieldArray0[5] = (ZipExtraField) resourceAlignmentExtraField0;
        UnparseableExtraFieldData unparseableExtraFieldData0 = new UnparseableExtraFieldData();
        zipExtraFieldArray0[6] = (ZipExtraField) unparseableExtraFieldData0;
        byte[] byteArray1 = ExtraFieldUtils.mergeLocalFileDataData(zipExtraFieldArray0);
        assertEquals(136, byteArray1.length);
    }
}
