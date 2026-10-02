package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Soundex_ESTest_test04 extends Soundex_ESTest_scaffolding {

    // Custom mapping string used for both the Soundex constructor and the input to encode
    private static final String CUSTOM_MAPPING = "9^n}]@7bH(,#/L";

    // Expected Soundex code produced when encoding CUSTOM_MAPPING with itself as the mapping table
    private static final String EXPECTED_SOUNDEX_CODE = "N^#0";

    // Soundex always produces exactly 4-character codes
    private static final int SOUNDEX_CODE_LENGTH = 4;

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        // Arrange: create a Soundex encoder using the custom mapping string
        Soundex soundex = new Soundex(CUSTOM_MAPPING);

        // Act: encode the same string as an Object (exercises the Object overload of encode)
        Object encodedResult = soundex.encode((Object) CUSTOM_MAPPING);

        // Assert: the default max-length is 4 and the encoded value matches the expected Soundex code
        assertEquals(SOUNDEX_CODE_LENGTH, soundex.getMaxLength());
        assertEquals(EXPECTED_SOUNDEX_CODE, encodedResult);
    }
}
