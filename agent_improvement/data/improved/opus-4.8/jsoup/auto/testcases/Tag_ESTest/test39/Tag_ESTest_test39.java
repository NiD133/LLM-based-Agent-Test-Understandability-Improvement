package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test39 extends Tag_ESTest_scaffolding {

    /**
     * A Tag built directly via the constructor (rather than through a TagSet)
     * has no options set, so it is not considered a "known" tag, and its
     * toString() simply returns the raw tag name.
     */
    @Test(timeout = 4000)
    public void constructedTagIsNotKnownAndToStringReturnsName() throws Throwable {
        String tagName = "|=$:#:mei1";
        Tag tag = new Tag(tagName, tagName, tagName);

        String asString = tag.toString();

        assertFalse("A directly constructed tag should not be marked as known", tag.isKnownTag());
        assertNotNull("toString() should return the tag name, never null", asString);
    }
}
