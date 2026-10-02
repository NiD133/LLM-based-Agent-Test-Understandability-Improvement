package org.apache.commons.lang3.text.translate;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LookupTranslator_ESTest_test2 extends LookupTranslator_ESTest_scaffolding {

    /**
     * Verifies that LookupTranslator can be constructed with a null lookup table
     * without throwing an exception. When null is passed, the constructor should
     * skip the table-population loop and produce a translator with an empty lookup map.
     */
    @Test(timeout = 4000)
    public void test_constructorAcceptsNullLookupTable() throws Throwable {
        // A null lookup table is a valid edge case; the constructor guards against it
        // with an explicit null check, so no NullPointerException should be thrown.
        LookupTranslator translatorWithNoEntries = new LookupTranslator((CharSequence[][]) null);
    }
}
