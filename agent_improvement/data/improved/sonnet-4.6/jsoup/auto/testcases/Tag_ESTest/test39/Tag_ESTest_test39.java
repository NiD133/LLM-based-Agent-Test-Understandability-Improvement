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
     * A Tag created via the package-private (tagName, normalName, namespace) constructor
     * does not set the Known option bit, so isKnownTag() must return false.
     * toString() is expected to return the tagName, which is non-null.
     */
    @Test(timeout = 4000)
    public void tagCreatedDirectlyWithoutKnownOptionIsNotAKnownTag() throws Throwable {
        String rawTagName = "|=$:#:mei1";
        Tag customTag = new Tag(rawTagName, rawTagName, rawTagName);

        String tagStringRepresentation = customTag.toString();

        assertFalse("Tag created via package-private constructor should not be a known tag",
            customTag.isKnownTag());
        assertNotNull("toString() should return the tag name, not null",
            tagStringRepresentation);
    }
}
