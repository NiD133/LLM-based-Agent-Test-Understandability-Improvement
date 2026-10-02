package org.apache.commons.compress.archivers.zip;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ExtraFieldUtils_ESTest_test15 extends ExtraFieldUtils_ESTest_scaffolding {

    /**
     * A four-byte all-zero buffer forms exactly one well-formed extra field:
     * header id 0x0000 followed by a declared data length of 0. Since header id
     * 0x0000 is not a registered implementation, it parses into a single
     * UnrecognizedExtraField, so the resulting array holds one element.
     */
    @Test(timeout = 4000)
    public void parseSingleEmptyExtraFieldFromCentralDirectory() throws Throwable {
        byte[] singleEmptyExtraField = new byte[4];

        ZipExtraField[] parsedFields = ExtraFieldUtils.parse(singleEmptyExtraField, false);

        assertEquals(1, parsedFields.length);
    }
}
