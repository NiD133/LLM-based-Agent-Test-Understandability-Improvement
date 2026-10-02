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
     * Verifies that {@code onNewTag} supports method chaining by returning the same
     * {@code TagSet} instance after each customizer is registered.
     */
    @Test(timeout = 4000)
    public void test02() throws Throwable {
        TagSet tagSet = new TagSet();

        Consumer<Tag> firstCustomizer = (Consumer<Tag>) mock(Consumer.class, new ViolatedAssumptionAnswer());
        tagSet.onNewTag(firstCustomizer);

        Consumer<Tag> secondCustomizer = (Consumer<Tag>) mock(Consumer.class, new ViolatedAssumptionAnswer());
        TagSet returnedTagSet = tagSet.onNewTag(secondCustomizer);

        // onNewTag should return 'this' to enable fluent chaining
        assertSame(tagSet, returnedTagSet);
    }
}
