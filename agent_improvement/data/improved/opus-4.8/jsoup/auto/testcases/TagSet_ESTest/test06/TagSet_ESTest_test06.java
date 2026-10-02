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

    /**
     * Registers an onNewTag customizer on the default HTML TagSet, then creates a
     * tag in a custom namespace and adds it. Verifies that the resulting Tag keeps
     * the requested name and namespace and is reported as a non-empty element.
     */
    @Test(timeout = 4000)
    public void test06() throws Throwable {
        String customNamespace = "'m0>xo>#liipqn'a?xs";

        TagSet htmlTagSet = TagSet.HtmlTagSet;
        ParseSettings htmlSettings = ParseSettings.htmlDefault;

        // A no-op customizer that will be invoked when tags are added to the set.
        Consumer<Tag> tagCustomizer = (Consumer<Tag>) mock(Consumer.class, new ViolatedAssumptionAnswer());
        TagSet customizedTagSet = htmlTagSet.onNewTag(tagCustomizer);

        // Create the "mtext" tag under the custom namespace and register it.
        Tag mtextTag = Tag.valueOf("mtext", customNamespace, htmlSettings);
        assertNotNull(mtextTag);

        customizedTagSet.add(mtextTag);

        assertEquals(customNamespace, mtextTag.namespace());
        assertEquals("mtext", mtextTag.normalName());
        assertEquals("mtext", mtextTag.toString());
        assertFalse(mtextTag.isEmpty());
        assertEquals("mtext", mtextTag.localName());
    }
}
