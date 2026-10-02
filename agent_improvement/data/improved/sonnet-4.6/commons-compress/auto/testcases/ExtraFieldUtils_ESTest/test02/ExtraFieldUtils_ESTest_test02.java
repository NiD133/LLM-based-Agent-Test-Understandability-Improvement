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
        // Build a 7-element array of mixed extra fields; UnparseableExtraFieldData must be last
        // because mergeLocalFileDataData treats the final element specially when it is unparseable.
        ZipExtraField[] extraFields = new ZipExtraField[7];

        UnicodePathExtraField unicodePath = new UnicodePathExtraField();
        extraFields[0] = unicodePath;
        extraFields[1] = new X000A_NTFS();
        extraFields[2] = unicodePath; // same instance reused intentionally
        extraFields[3] = new UnicodeCommentExtraField("Error parsing extra fields for entry: ", new byte[8]);
        extraFields[4] = new X0015_CertificateIdForFile();
        extraFields[5] = new ResourceAlignmentExtraField((byte) 4, true, (byte) 35);
        extraFields[6] = new UnparseableExtraFieldData(); // unparseable holder — kept last

        byte[] merged = ExtraFieldUtils.mergeLocalFileDataData(extraFields);
        assertEquals(136, merged.length);
    }
}
