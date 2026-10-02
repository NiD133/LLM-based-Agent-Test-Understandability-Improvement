package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test35 extends Tag_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test35() throws Throwable {
        String customNamespace = "org.jsoup.parser.Tag";

        // Create a tag whose tagName and initial namespace are both the custom value
        Tag tag = new Tag(customNamespace, customNamespace);

        // namespace(String) is a fluent setter; it updates the namespace and returns the same tag
        Tag tagWithNamespaceSet = tag.namespace(customNamespace);

        // The namespace should reflect the value just assigned
        assertEquals(customNamespace, tag.namespace());

        // normalName() is the lowercased tag name, so "org.jsoup.parser.Tag" becomes "org.jsoup.parser.tag"
        assertEquals("org.jsoup.parser.tag", tagWithNamespaceSet.normalName());

        // Tags created directly (not registered in a TagSet) are not considered known tags
        assertFalse(tagWithNamespaceSet.isKnownTag());
    }
}
