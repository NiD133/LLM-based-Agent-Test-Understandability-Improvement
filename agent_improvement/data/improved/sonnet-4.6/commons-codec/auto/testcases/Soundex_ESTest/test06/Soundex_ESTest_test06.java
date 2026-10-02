package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Soundex_ESTest_test06 extends Soundex_ESTest_scaffolding {

    // Genealogy mapping where '-' marks silent letters (vowels, H, W are all ignored)
    private static final String GENEALOGY_MAPPING = "-123-12--22455-12623-1-2-2";

    @Test(timeout = 4000)
    public void test06() throws Throwable {
        // Soundex codes are always 4 characters long by definition, regardless of the mapping used
        Soundex soundex = new Soundex(GENEALOGY_MAPPING);
        assertEquals(4, soundex.getMaxLength());
    }
}
