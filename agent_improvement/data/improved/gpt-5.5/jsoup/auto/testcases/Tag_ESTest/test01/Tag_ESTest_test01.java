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

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        // Arrange: construct tags with the same name but different namespaces.
        Tag namespacedTag = new Tag("t", "t");
        Tag htmlNamespaceTag = new Tag("t");

        // Act: keep the generated equality check as a named result for the first assertion.
        boolean tagsAreEqual = namespacedTag.equals(htmlNamespaceTag);

        // Assert: the namespace difference keeps the tags unequal, and the direct constructor leaves tags unknown.
        assertFalse(tagsAreEqual);
        assertFalse(htmlNamespaceTag.isKnownTag());
        assertFalse(htmlNamespaceTag.equals((Object) namespacedTag));
    }
}
