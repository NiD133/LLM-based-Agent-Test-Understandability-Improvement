package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test40 extends Tag_ESTest_scaffolding {

    /**
     * Setting the {@code Void} option (value 2 == {@code Tag.Void}) marks the tag as a void/empty
     * tag. Because {@link Tag#set(int)} returns the same tag instance, and a void tag is also
     * reported as self-closing, both {@code isEmpty()} and {@code isSelfClosing()} become true.
     */
    @Test(timeout = 4000)
    public void settingVoidOptionMakesTagEmptyAndSelfClosing() throws Throwable {
        Tag tag = new Tag("A");

        // set() mutates and returns the same instance, so this is the same tag object.
        Tag sameTag = tag.set(Tag.Void);

        assertTrue("a void tag should report as self-closing", sameTag.isSelfClosing());
        assertTrue("setting the Void option should make the tag empty", tag.isEmpty());
    }
}
