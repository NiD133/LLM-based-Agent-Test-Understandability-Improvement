package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TagSet_ESTest_test08 extends TagSet_ESTest_scaffolding {

    /**
     * A TagSet created by copying another (via the copy constructor) should be
     * considered equal to its template, since equality is based on the contained tags.
     */
    @Test(timeout = 4000)
    public void copyOfHtmlTagSetEqualsOriginal() throws Throwable {
        TagSet htmlTagSet = TagSet.Html();
        TagSet copyOfHtmlTagSet = new TagSet(htmlTagSet);

        assertTrue(copyOfHtmlTagSet.equals(htmlTagSet));
    }
}
