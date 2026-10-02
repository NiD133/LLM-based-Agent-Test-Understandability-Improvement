/*
 * Improved version of the EvoSuite-generated test for TagSet.
 * Refactored for understandability: descriptive test names, meaningful variable names,
 * and brief comments explaining non-obvious intent. Runtime behaviour is unchanged.
 */

package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import java.util.function.Consumer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.jsoup.parser.ParseSettings;
import org.jsoup.parser.Tag;
import org.jsoup.parser.TagSet;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TagSet_ESTest extends TagSet_ESTest_scaffolding {

  @Test(timeout = 4000)
  public void equals_withNonTagSetObject_returnsFalse() throws Throwable {
      TagSet tagSet = new TagSet();
      Object plainObject = new Object();
      boolean result = tagSet.equals(plainObject);
      assertFalse(result);
  }

  @Test(timeout = 4000)
  public void equals_withSameInstance_returnsTrue() throws Throwable {
      TagSet tagSet = TagSet.Html();
      boolean result = tagSet.equals(tagSet);
      assertTrue(result);
  }

  @Test(timeout = 4000)
  public void onNewTag_returnsThisForMethodChaining() throws Throwable {
      TagSet tagSet = new TagSet();
      Consumer<Tag> firstConsumer = (Consumer<Tag>) mock(Consumer.class, new ViolatedAssumptionAnswer());
      tagSet.onNewTag(firstConsumer);
      Consumer<Tag> secondConsumer = (Consumer<Tag>) mock(Consumer.class, new ViolatedAssumptionAnswer());
      TagSet returnedTagSet = tagSet.onNewTag(secondConsumer);
      assertSame(tagSet, returnedTagSet);
  }

  @Test(timeout = 4000)
  public void valueOf_withSameNormalName_returnsSameTagInstance() throws Throwable {
      TagSet tagSet = new TagSet();
      // Both calls share the same normalName and preserveTagCase=false, so the same Tag is returned
      Tag firstTag = tagSet.valueOf("v;;K-", "~m&2\"v*M>Y$C[<", "", false);
      Tag secondTag = tagSet.valueOf("J", "~m&2\"v*M>Y$C[<", "", false);
      assertSame(firstTag, secondTag);
  }

  @Test(timeout = 4000)
  public void valueOf_withPreserveTagCase_preservesOriginalTagName() throws Throwable {
      TagSet tagSet = new TagSet();
      Tag storedTag = tagSet.valueOf("v;;K-", "~m&2\"v*M>Y$C[<", "", false);

      // preserveTagCase=true causes valueOf to clone the existing tag and store the original name "J"
      Tag preservedTag = tagSet.valueOf("J", "~m&2\"v*M>Y$C[<", "", true);
      assertEquals("J", preservedTag.getName());
  }

  @Test(timeout = 4000)
  public void valueOf_knownHtmlTag_isKnownTag() throws Throwable {
      Tag kbdTag = Tag.valueOf("kbd");
      assertTrue(kbdTag.isKnownTag());
  }

  @Test(timeout = 4000)
  public void add_withCustomizerRegistered_appliesCustomizerToAddedTag() throws Throwable {
      TagSet htmlTagSet = TagSet.HtmlTagSet;
      ParseSettings htmlSettings = ParseSettings.htmlDefault;
      Consumer<Tag> customizer = (Consumer<Tag>) mock(Consumer.class, new ViolatedAssumptionAnswer());
      TagSet tagSetWithCustomizer = htmlTagSet.onNewTag(customizer);
      Tag mTextTag = Tag.valueOf("mtext", "'m0>xo>#liipqn'a?xs", htmlSettings);

      tagSetWithCustomizer.add(mTextTag);
      verify(customizer).accept(mTextTag);
  }

  @Test(timeout = 4000)
  public void copyConstructor_withCustomizerRegistered_createsEqualCopy() throws Throwable {
      TagSet htmlTagSet = TagSet.HtmlTagSet;
      Consumer<Tag> customizer = (Consumer<Tag>) mock(Consumer.class, new ViolatedAssumptionAnswer());
      doReturn((String) null).when(customizer).toString();
      htmlTagSet.onNewTag(customizer);
      TagSet copy = new TagSet(htmlTagSet);
      assertEquals(htmlTagSet, copy);
  }

  @Test(timeout = 4000)
  public void copyConstructor_fromHtmlTagSet_createsEqualCopy() throws Throwable {
      TagSet originalHtmlTagSet = TagSet.Html();
      TagSet copy = new TagSet(originalHtmlTagSet);
      assertEquals(originalHtmlTagSet, copy);
  }

  @Test(timeout = 4000)
  public void copyConstructor_withTagsPresent_copiesTagsAndCreatesEqualCopy() throws Throwable {
      TagSet tagSet = new TagSet();
      Tag createdTag = tagSet.valueOf("~m&2\"v*M>Y$C[<", "~m&2\"v*M>Y$C[<");

      TagSet copy = new TagSet(tagSet);
      assertEquals(tagSet, copy);
  }

  @Test(timeout = 4000)
  public void hashCode_onEmptyTagSet_doesNotThrow() throws Throwable {
      TagSet tagSet = new TagSet();
      assertEquals(0, tagSet.hashCode());
  }

  @Test(timeout = 4000)
  public void initHtmlDefault_returnsNonNullTagSet() throws Throwable {
      TagSet htmlDefaultTagSet = TagSet.initHtmlDefault();
      assertNotNull(htmlDefaultTagSet);
  }
}
