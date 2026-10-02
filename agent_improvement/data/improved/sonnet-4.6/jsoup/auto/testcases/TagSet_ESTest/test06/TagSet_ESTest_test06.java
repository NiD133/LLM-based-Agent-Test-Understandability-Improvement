package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import java.util.function.Consumer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TagSet_ESTest_test06 extends TagSet_ESTest_scaffolding {

    // Verifies that a tag created in a custom namespace retains its name and namespace properties
    // after being added to a TagSet that has an onNewTag customizer registered.
    @Test(timeout = 4000)
    public void test06() throws Throwable {
        TagSet htmlTagSet = TagSet.HtmlTagSet;
        ParseSettings htmlParseSettings = ParseSettings.htmlDefault;

        // Register a no-op mock customizer so that onNewTag code path is exercised
        Consumer<Tag> tagCustomizer = (Consumer<Tag>) mock(Consumer.class, new ViolatedAssumptionAnswer());
        TagSet tagSetWithCustomizer = htmlTagSet.onNewTag(tagCustomizer);

        // Create a tag in a deliberately unusual (non-standard) namespace
        String customNamespace = "'m0>xo>#liipqn'a?xs";
        Tag mtextTag = Tag.valueOf("mtext", customNamespace, htmlParseSettings);
        assertNotNull(mtextTag);

        // Adding the tag should invoke the customizer and store the tag
        tagSetWithCustomizer.add(mtextTag);

        // The tag's namespace, names, and emptiness should remain unchanged after being added
        assertEquals(customNamespace, mtextTag.namespace());
        assertEquals("mtext", mtextTag.normalName());
        assertEquals("mtext", mtextTag.toString());
        assertFalse(mtextTag.isEmpty());
        assertEquals("mtext", mtextTag.localName());
    }
}
