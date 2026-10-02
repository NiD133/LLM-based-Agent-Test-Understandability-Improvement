package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Set;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharSet_ESTest_test9 extends CharSet_ESTest_scaffolding {

    /**
     * A CharSet built from a single null set-definition string should contain
     * no character ranges, because null definitions are ignored when parsing.
     */
    @Test(timeout = 4000)
    public void getCharRanges_withNullDefinition_returnsEmptySet() throws Throwable {
        String[] setDefinitions = new String[1]; // one element, defaulting to null
        CharSet charSet = CharSet.getInstance(setDefinitions);

        Set<CharRange> charRanges = charSet.getCharRanges();

        assertEquals(0, charRanges.size());
    }
}
