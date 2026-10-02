package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test35 extends Tag_ESTest_scaffolding {

    /**
     * Verifies that {@link Tag#namespace(String)} updates the tag's namespace
     * and returns the same Tag instance (fluent API), while the normalized name
     * remains the lower-cased tag name. Since the tag was created directly rather
     * than registered, it is not considered a known tag.
     */
    @Test(timeout = 4000)
    public void namespaceSetterUpdatesNamespaceAndReturnsSameTag() throws Throwable {
        String mixedCaseName = "org.jsoup.parser.Tag";
        Tag tag = new Tag(mixedCaseName, mixedCaseName);

        Tag returnedTag = tag.namespace("org.jsoup.parser.Tag");

        // namespace() reflects the value just set
        assertEquals("org.jsoup.parser.Tag", tag.namespace());
        // normalName is the lower-cased form of the tag name
        assertEquals("org.jsoup.parser.tag", returnedTag.normalName());
        // a directly constructed tag is not a known tag
        assertFalse(returnedTag.isKnownTag());
    }
}
