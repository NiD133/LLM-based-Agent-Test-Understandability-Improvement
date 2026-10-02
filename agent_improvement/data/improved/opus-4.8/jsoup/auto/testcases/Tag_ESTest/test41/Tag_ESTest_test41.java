package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test41 extends Tag_ESTest_scaffolding {

    /**
     * Tag.valueOf(tagName, settings) dereferences the supplied ParseSettings to
     * read its case-preservation flag. Passing a null ParseSettings must therefore
     * fail with a NullPointerException thrown from within the Tag class.
     */
    @Test(timeout = 4000)
    public void valueOf_withNullParseSettings_throwsNullPointerException() throws Throwable {
        try {
            Tag.valueOf("", (ParseSettings) null);
            fail("Expected a NullPointerException when ParseSettings is null");
        } catch (NullPointerException e) {
            // The exception originates inside org.jsoup.parser.Tag and carries no message.
            verifyException("org.jsoup.parser.Tag", e);
        }
    }
}
