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
        TagSet tagSet0 = TagSet.HtmlTagSet;
        Consumer<Tag> consumer0 = (Consumer<Tag>) mock(Consumer.class, new ViolatedAssumptionAnswer());
        doReturn((String) null).when(consumer0).toString();
        tagSet0.onNewTag(consumer0);
        TagSet tagSet1 = new TagSet(tagSet0);
        assertTrue(tagSet1.equals((Object) tagSet0));
    }
}
