package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test17 extends Tag_ESTest_scaffolding {

    /**
     * A tag is "empty" (void) exactly when its Void option bit is set in {@code options}.
     * A freshly created tag has no options, so it is not empty; once the Void bit
     * (value 2) is enabled, {@link Tag#isEmpty()} reports true.
     */
    @Test(timeout = 4000)
    public void isEmptyReflectsVoidOptionBit() throws Throwable {
        Tag tag = new Tag("_F31ld-BAJ[");

        // No options set yet, so the tag is not void/empty.
        assertFalse(tag.isEmpty());

        // Enable the Void option bit (Tag.Void == 1 << 1 == 2).
        tag.options = 2;

        assertTrue(tag.isEmpty());
    }
}
