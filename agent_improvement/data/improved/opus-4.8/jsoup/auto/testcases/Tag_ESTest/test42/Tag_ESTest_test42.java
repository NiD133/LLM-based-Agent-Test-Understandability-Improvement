package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test42 extends Tag_ESTest_scaffolding {

    /**
     * A Tag built directly via the constructor (rather than through a TagSet)
     * keeps the exact name it was given and is not flagged as a known tag,
     * because no options have been applied to it.
     */
    @Test(timeout = 4000)
    public void constructedTagReportsItsNameAndIsNotKnown() throws Throwable {
        Tag emptyNameTag = new Tag("", "");

        String name = emptyNameTag.name();

        assertNotNull("name() should never return null", name);
        assertFalse("a freshly constructed tag should not be a known tag", emptyNameTag.isKnownTag());
    }
}
