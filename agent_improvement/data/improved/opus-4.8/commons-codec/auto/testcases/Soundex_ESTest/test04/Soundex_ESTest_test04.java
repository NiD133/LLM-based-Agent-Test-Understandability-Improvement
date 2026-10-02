package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Soundex_ESTest_test04 extends Soundex_ESTest_scaffolding {

    /**
     * Verifies encoding through the generic {@link Soundex#encode(Object)} entry point
     * when the instance is built with a custom (non US-English) mapping string.
     *
     * <p>The input contains punctuation and digits; {@code SoundexUtils.clean} strips
     * everything except letters, leaving only "nbHL". With the custom mapping the
     * algorithm uppercases this to "NBHL" and resolves it to the four-character
     * Soundex code "N^#0".</p>
     */
    @Test(timeout = 4000)
    public void encodeObjectWithCustomMappingReturnsSoundexCode() throws Throwable {
        String customMapping = "9^n}]@7bH(,#/L";
        Soundex soundex = new Soundex(customMapping);

        Object encoded = soundex.encode((Object) "9^n}]@7bH(,#/L");

        assertEquals("N^#0", encoded);
        assertEquals(4, soundex.getMaxLength());
    }
}
