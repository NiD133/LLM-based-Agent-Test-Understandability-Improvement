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
        // Obtain the shared default HTML tag set and register a mock tag customizer on it
        TagSet htmlTagSet = TagSet.HtmlTagSet;
        Consumer<Tag> mockTagCustomizer = (Consumer<Tag>) mock(Consumer.class, new ViolatedAssumptionAnswer());
        doReturn((String) null).when(mockTagCustomizer).toString();
        htmlTagSet.onNewTag(mockTagCustomizer);

        // Create a copy of the tag set (copy constructor carries over source and customizers)
        TagSet copiedTagSet = new TagSet(htmlTagSet);

        // TagSet.equals compares only the tags map, so the copy equals the original
        assertTrue(copiedTagSet.equals((Object) htmlTagSet));
    }
}
