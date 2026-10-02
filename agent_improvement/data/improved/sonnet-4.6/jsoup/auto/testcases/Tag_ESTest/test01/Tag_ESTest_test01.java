package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test01 extends Tag_ESTest_scaffolding {

    // Tags with the same name but different namespaces are not equal,
    // and tags created directly (not from a TagSet) are not considered "known".
    @Test(timeout = 4000)
    public void test01() throws Throwable {
        // Tag "t" in a custom namespace "t"
        Tag tagWithCustomNamespace = new Tag("t", "t");
        // Tag "t" in the default HTML namespace
        Tag tagWithHtmlNamespace = new Tag("t");

        // Tags differ by namespace, so equals() should return false
        boolean tagsAreEqual = tagWithCustomNamespace.equals(tagWithHtmlNamespace);
        assertFalse(tagsAreEqual);

        // A tag created via constructor (not registered in a TagSet) is not a known tag
        assertFalse(tagWithHtmlNamespace.isKnownTag());

        // Equality is symmetric: the HTML-namespace tag also does not equal the custom-namespace tag
        assertFalse(tagWithHtmlNamespace.equals((Object) tagWithCustomNamespace));
    }
}
