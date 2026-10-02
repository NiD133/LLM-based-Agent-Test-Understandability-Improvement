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

    /**
     * A copy made with the {@code TagSet(TagSet)} constructor should be equal to its template,
     * even after a customizer has been registered on the template via {@link TagSet#onNewTag}.
     * Equality is defined purely by the contained tags, so registering a customizer does not
     * break the copy/template equality.
     */
    @Test(timeout = 4000)
    public void copyOfTemplateEqualsTemplate() throws Throwable {
        TagSet template = TagSet.HtmlTagSet;

        // Register a no-op tag customizer on the template.
        Consumer<Tag> tagCustomizer = (Consumer<Tag>) mock(Consumer.class, new ViolatedAssumptionAnswer());
        doReturn((String) null).when(tagCustomizer).toString();
        template.onNewTag(tagCustomizer);

        TagSet copy = new TagSet(template);

        assertTrue("Copy should equal the template it was created from", copy.equals(template));
    }
}
