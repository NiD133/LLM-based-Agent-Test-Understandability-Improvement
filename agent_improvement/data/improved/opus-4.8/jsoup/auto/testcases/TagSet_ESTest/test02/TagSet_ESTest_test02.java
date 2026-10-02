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
public class TagSet_ESTest_test02 extends TagSet_ESTest_scaffolding {

    /**
     * onNewTag registers a tag customizer and returns the same TagSet so that
     * calls can be chained. This verifies that registering a second customizer
     * on an already-customized TagSet still returns that same instance.
     */
    @Test(timeout = 4000)
    public void onNewTag_returnsSameTagSetForChaining() throws Throwable {
        TagSet tagSet = new TagSet();

        Consumer<Tag> firstCustomizer = (Consumer<Tag>) mock(Consumer.class, new ViolatedAssumptionAnswer());
        tagSet.onNewTag(firstCustomizer);

        Consumer<Tag> secondCustomizer = (Consumer<Tag>) mock(Consumer.class, new ViolatedAssumptionAnswer());
        TagSet returnedTagSet = tagSet.onNewTag(secondCustomizer);

        assertSame(tagSet, returnedTagSet);
    }
}
