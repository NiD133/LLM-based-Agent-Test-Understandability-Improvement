package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Soundex_ESTest_test08 extends Soundex_ESTest_scaffolding {

    /**
     * When the US_ENGLISH Soundex encoder compares a string against itself,
     * the difference score should be 0 because the raw mapping string is not
     * a valid name (it consists only of digits) and encodes to a empty/zero result.
     *
     * Note: soundex0 is assigned US_ENGLISH_GENEALOGY but the difference() call
     * is made on US_ENGLISH (accessed as a static field). The two Soundex instances
     * use different phonetic mappings, so this test specifically exercises US_ENGLISH.
     */
    @Test(timeout = 4000)
    public void test08() throws Throwable {
        // soundex0 holds the genealogy variant, but difference() is called on the
        // US_ENGLISH static field (accessed through the instance reference).
        Soundex soundex0 = Soundex.US_ENGLISH_GENEALOGY;
        int differenceScore = soundex0.US_ENGLISH.difference(
            Soundex.US_ENGLISH_MAPPING_STRING,
            Soundex.US_ENGLISH_MAPPING_STRING
        );
        // The mapping string contains only digits, so both inputs encode to the same
        // degenerate Soundex code, yielding a difference score of 0.
        assertEquals(0, differenceScore);
    }
}
