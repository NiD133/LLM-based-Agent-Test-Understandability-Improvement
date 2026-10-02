package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test01 extends Tag_ESTest_scaffolding {

    /**
     * Two tags that share the same name but differ in namespace must not be
     * considered equal, and that inequality must hold in both directions.
     */
    @Test(timeout = 4000)
    public void tagsWithSameNameButDifferentNamespaceAreNotEqual() throws Throwable {
        Tag tagInCustomNamespace = new Tag("t", "t");   // name "t", namespace "t"
        Tag tagInHtmlNamespace = new Tag("t");          // name "t", default HTML namespace

        assertFalse("Tags with different namespaces should not be equal",
                tagInCustomNamespace.equals(tagInHtmlNamespace));
        assertFalse("equals() should be symmetric",
                tagInHtmlNamespace.equals((Object) tagInCustomNamespace));

        assertFalse("A freshly created tag is not a known/predefined tag",
                tagInHtmlNamespace.isKnownTag());
    }
}
