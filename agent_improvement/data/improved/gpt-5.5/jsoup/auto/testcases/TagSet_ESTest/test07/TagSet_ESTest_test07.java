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
public class TagSet_ESTest_test07 extends TagSet_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test07() throws Throwable {
        TagSet htmlTagSet = TagSet.HtmlTagSet;
        Consumer<Tag> newTagCustomizer = (Consumer<Tag>) mock(Consumer.class, new ViolatedAssumptionAnswer());
        doReturn((String) null).when(newTagCustomizer).toString();

        htmlTagSet.onNewTag(newTagCustomizer);
        TagSet copiedTagSet = new TagSet(htmlTagSet);

        assertTrue(copiedTagSet.equals((Object) htmlTagSet));
    }
}
