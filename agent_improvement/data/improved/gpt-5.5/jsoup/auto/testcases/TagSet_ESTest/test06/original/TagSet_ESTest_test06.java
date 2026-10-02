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

    @Test(timeout = 4000)
    public void test06() throws Throwable {
        TagSet tagSet0 = TagSet.HtmlTagSet;
        ParseSettings parseSettings0 = ParseSettings.htmlDefault;
        Consumer<Tag> consumer0 = (Consumer<Tag>) mock(Consumer.class, new ViolatedAssumptionAnswer());
        TagSet tagSet1 = tagSet0.onNewTag(consumer0);
        Tag tag0 = Tag.valueOf("mtext", "'m0>xo>#liipqn'a?xs", parseSettings0);
        assertNotNull(tag0);
        tagSet1.add(tag0);
        assertEquals("'m0>xo>#liipqn'a?xs", tag0.namespace());
        assertEquals("mtext", tag0.normalName());
        assertEquals("mtext", tag0.toString());
        assertFalse(tag0.isEmpty());
        assertEquals("mtext", tag0.localName());
    }
}
