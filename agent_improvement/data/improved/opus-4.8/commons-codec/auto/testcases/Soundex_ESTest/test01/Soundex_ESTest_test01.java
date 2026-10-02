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
     * Encoding a null String should be handled gracefully: the US_ENGLISH
     * Soundex returns null rather than throwing.
     */
    @Test(timeout = 4000)
    public void encodingNullStringReturnsNull() throws Throwable {
        Soundex usEnglishSoundex = Soundex.US_ENGLISH;

        String encoded = usEnglishSoundex.encode((String) null);

        assertNull(encoded);
    }
}
