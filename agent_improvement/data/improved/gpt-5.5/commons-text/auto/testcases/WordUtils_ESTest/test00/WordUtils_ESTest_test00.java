package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class WordUtils_ESTest_test00 extends WordUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        final String input = "|eT ($ElK)2^p:";
        final int wrapLength = -3137;
        final String newLineString = "|eT ($ElK)2^p:";
        final boolean wrapLongWords = false;
        final String wrapOn = "|eT ($ElK)2^p:";

        final String wrapped = WordUtils.wrap(input, wrapLength, newLineString, wrapLongWords, wrapOn);

        assertEquals("eT|eT ($ElK)2^p:$E|eT ($ElK)2^p:)2|eT ($ElK)2^p:p:", wrapped);
    }
}
