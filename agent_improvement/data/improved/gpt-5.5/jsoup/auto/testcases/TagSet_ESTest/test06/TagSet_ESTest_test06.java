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

    private static final String TAG_NAME = "mtext";
    private static final String NAMESPACE = "'m0>xo>#liipqn'a?xs";

    @Test(timeout = 4000)
    public void test06() throws Throwable {
        TagSet htmlTagSet = TagSet.HtmlTagSet;
        ParseSettings htmlParseSettings = ParseSettings.htmlDefault;
        Consumer<Tag> newTagCustomizer = (Consumer<Tag>) mock(Consumer.class, new ViolatedAssumptionAnswer());

        TagSet customizedTagSet = htmlTagSet.onNewTag(newTagCustomizer);
        Tag mathTextTag = Tag.valueOf(TAG_NAME, NAMESPACE, htmlParseSettings);

        assertNotNull(mathTextTag);
        customizedTagSet.add(mathTextTag);

        assertEquals(NAMESPACE, mathTextTag.namespace());
        assertEquals(TAG_NAME, mathTextTag.normalName());
        assertEquals(TAG_NAME, mathTextTag.toString());
        assertFalse(mathTextTag.isEmpty());
        assertEquals(TAG_NAME, mathTextTag.localName());
    }
}
