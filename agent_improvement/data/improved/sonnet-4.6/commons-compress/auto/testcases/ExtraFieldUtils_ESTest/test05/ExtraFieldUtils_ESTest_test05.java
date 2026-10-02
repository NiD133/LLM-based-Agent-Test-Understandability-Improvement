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
public class ExtraFieldUtils_ESTest_test05 extends ExtraFieldUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test05_mergeCentralDirectoryData_singleUnicodePathField_returnsHeaderIdAndZeroLength() throws Throwable {
        UnicodePathExtraField unicodePathField = new UnicodePathExtraField();
        ZipExtraField[] fields = new ZipExtraField[] { unicodePathField };

        byte[] mergedData = ExtraFieldUtils.mergeCentralDirectoryData(fields);

        // Expected layout: 2-byte header ID ('u'=0x75, 'p'=0x70) + 2-byte zero data length (0x00, 0x00)
        byte[] expectedBytes = new byte[] { (byte) 0x75, (byte) 0x70, (byte) 0x00, (byte) 0x00 };
        assertArrayEquals(expectedBytes, mergedData);
    }
}
