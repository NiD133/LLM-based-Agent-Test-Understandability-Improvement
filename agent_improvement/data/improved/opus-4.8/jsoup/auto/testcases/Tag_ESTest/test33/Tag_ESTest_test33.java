package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test33 extends Tag_ESTest_scaffolding {

    /**
     * A Tag constructed directly (not added to a TagSet and with no options applied)
     * is not considered "known". Reading its prefix does not change that status.
     */
    @Test(timeout = 4000)
    public void prefixCallDoesNotMakeTagKnown() throws Throwable {
        // A tag name without a ':' separator, so it has no namespace prefix.
        String tagName = "iP_$Km@lI4Ix)VZ";
        Tag tag = new Tag(tagName, tagName, tagName);

        // prefix() simply inspects the name; it must not flip the tag to "known".
        tag.prefix();

        assertFalse(tag.isKnownTag());
    }
}
