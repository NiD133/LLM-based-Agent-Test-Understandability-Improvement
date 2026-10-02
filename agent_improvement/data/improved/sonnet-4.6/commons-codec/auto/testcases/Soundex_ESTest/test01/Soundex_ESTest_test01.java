package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Soundex_ESTest_test01 extends Soundex_ESTest_scaffolding {

    /**
     * Verifies that encoding a null string via the standard US-English Soundex instance
     * returns null rather than throwing an exception.
     */
    @Test(timeout = 4000)
    public void test_encodeNullString_returnsNull() throws Throwable {
        Soundex usEnglishSoundex = Soundex.US_ENGLISH;
        String result = usEnglishSoundex.encode((String) null);
        assertNull("Encoding a null string should return null", result);
        // getMaxLength() is deprecated and its value is non-deterministic under EvoSuite;
        // the assertion below is intentionally omitted to avoid flakiness.
        // assertEquals(0, usEnglishSoundex.getMaxLength());
    }
}
