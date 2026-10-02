package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test12 extends Tag_ESTest_scaffolding {

    /**
     * isKnownTag should report false for a name that is not a defined HTML tag,
     * such as one containing arbitrary, non-alphabetic characters.
     */
    @Test(timeout = 4000)
    public void isKnownTagReturnsFalseForUnrecognizedName() throws Throwable {
        boolean isKnown = Tag.isKnownTag("iLUa^");

        assertFalse("A name that is not a defined HTML tag should not be known", isKnown);
    }
}
