package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Soundex_ESTest_test07 extends Soundex_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test07_defaultMaxLengthIsFourWhenUsingCustomCharMapping() throws Throwable {
        // A single-element mapping containing the SILENT_MARKER character ('-')
        // is used to construct a Soundex instance with a custom mapping.
        char[] customMapping = new char[1];
        customMapping[0] = Soundex.SILENT_MARKER;

        Soundex soundex = new Soundex(customMapping);

        // Regardless of the custom mapping provided, getMaxLength() must return 4,
        // which is the fixed length defined by the Soundex standard.
        assertEquals(4, soundex.getMaxLength());
    }
}
