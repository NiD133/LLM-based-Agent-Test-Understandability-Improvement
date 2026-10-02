package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test16 extends Tag_ESTest_scaffolding {

    /**
     * Verifies that {@link Tag#set(int)} turns on each requested option flag.
     * <p>
     * The combined options bitmask {@code 28} is {@code Block | InlineContainer | SelfClose}
     * (4 | 8 | 16). After setting it we expect:
     * <ul>
     *   <li>{@link Tag#isSelfClosing()} to be {@code true} because the {@code SelfClose} flag is on, and</li>
     *   <li>{@link Tag#formatAsBlock()} to be {@code true} because the {@code InlineContainer} flag is on.</li>
     * </ul>
     */
    @Test(timeout = 4000)
    public void settingCombinedOptionsEnablesSelfClosingAndBlockFormatting() throws Throwable {
        // 28 == Block (4) | InlineContainer (8) | SelfClose (16)
        int combinedOptions = Tag.Block | Tag.InlineContainer | Tag.SelfClose;

        Tag tag = new Tag("A");
        Tag updatedTag = tag.set(combinedOptions);

        // set() returns the same tag instance, so both the InlineContainer and SelfClose flags are now on.
        assertTrue("InlineContainer flag should make formatAsBlock() true", tag.formatAsBlock());
        assertTrue("SelfClose flag should make isSelfClosing() true", updatedTag.isSelfClosing());
    }
}
