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
     * Constructing a LookupTranslator with a {@code null} lookup table should
     * succeed without throwing. The constructor guards against a null table and
     * simply leaves its internal lookup map empty.
     */
    @Test(timeout = 4000)
    public void constructorAcceptsNullLookupTable() throws Throwable {
        CharSequence[][] nullLookupTable = null;

        LookupTranslator translator = new LookupTranslator(nullLookupTable);

        assertNotNull(translator);
    }
}
