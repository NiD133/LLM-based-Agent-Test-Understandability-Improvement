package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class WordUtils_ESTest_test40 extends WordUtils_ESTest_scaffolding {

    /**
     * When wrapLength is shorter than every word, each word is placed on its own line.
     * The mocked JVM uses "\r\n" as the system line separator.
     */
    @Test(timeout = 4000)
    public void test_wrapWithShortWrapLength_placesEachWordOnItsOwnLine() throws Throwable {
        String input = "upper value cannot be less than -1";
        int wrapLength = 2; // shorter than any word in the input

        String wrappedText = WordUtils.wrap(input, wrapLength);

        assertNotNull(wrappedText);
        assertEquals("upper\r\nvalue\r\ncannot\r\nbe\r\nless\r\nthan\r\n-1", wrappedText);
    }
}
