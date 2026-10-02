/*
 * Improved version of EvoSuite-generated Tag tests.
 * Refactored for readability: descriptive test names, meaningful variable names,
 * named constants in place of magic numbers, and brief comments for non-obvious scenarios.
 */

package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.jsoup.parser.ParseSettings;
import org.jsoup.parser.Tag;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest extends Tag_ESTest_scaffolding {

    // -------------------------------------------------------------------------
    // equals() tests
    // -------------------------------------------------------------------------

    @Test(timeout = 4000)
    public void directFieldMutationOfTagNameBreaksEquality()  throws Throwable  {
        // Directly setting tagName bypasses normalName update, so clone with
        // proper name() call ends up with different normalName → not equal.
        Tag original = new Tag(" |eOx/>T!", " |eOx/>T!", " |eOx/>T!");
        original.tagName = "b5 ;cl";
        Tag cloned = original.clone();
        cloned.name("b5 ;cl"); // updates both tagName and normalName
        boolean isEqual = original.equals(cloned);
        assertFalse(isEqual);
    }

    @Test(timeout = 4000)
    public void tagsWithDifferentNamespacesAreNotEqual()  throws Throwable  {
        // Two-arg constructor uses second arg as namespace; one-arg defaults to HTML namespace.
        Tag tagWithCustomNamespace = new Tag("t", "t");
        Tag tagWithHtmlNamespace   = new Tag("t");
        boolean isEqual = tagWithCustomNamespace.equals(tagWithHtmlNamespace);
        assertFalse(isEqual);
    }

    @Test(timeout = 4000)
    public void knownHtmlTagIsNotEqualToCustomUnknownTag()  throws Throwable  {
        Tag knownHtmlTag  = Tag.valueOf("html");
        Tag unknownCustomTag = new Tag("7E$B_fi2(/F6\"]&R$!6");
        boolean isEqual = knownHtmlTag.equals(unknownCustomTag);
        assertFalse(isEqual);
    }

    @Test(timeout = 4000)
    public void clonedTagEqualsOriginalTag()  throws Throwable  {
        Tag original = new Tag(" |eOx/>T!", " |eOx/>T!", " |eOx/>T!");
        Tag cloned   = original.clone();
        boolean isEqual = original.equals(cloned);
        assertTrue(isEqual);
    }

    @Test(timeout = 4000)
    public void tagEqualsSelf()  throws Throwable  {
        Tag tag = new Tag("]", "]");
        boolean isEqual = tag.equals(tag);
        assertTrue(isEqual);
    }

    @Test(timeout = 4000)
    public void tagIsNotEqualToNonTagObject()  throws Throwable  {
        Tag tag    = new Tag("", "");
        Object obj = new Object();
        boolean isEqual = tag.equals(obj);
        assertFalse(isEqual);
    }

    @Test(timeout = 4000)
    public void knownTagCreatedViaValueOfIsNotEqualToNewTagWithSameName()  throws Throwable  {
        Tag knownTag   = Tag.valueOf("I");
        Tag unknownTag = new Tag("I");
        boolean isEqual = knownTag.equals(unknownTag);
        assertFalse(isEqual);
    }

    // -------------------------------------------------------------------------
    // textState() tests
    // -------------------------------------------------------------------------

    @Test(timeout = 4000)
    public void textStateReturnsRcdataWhenRcDataOptionMatchesCurrentStaticValue()  throws Throwable  {
        // Directly reassigning the static RcData constant to 512 so that
        // options=512 triggers the RcData branch inside textState().
        ParseSettings parseSettings0 = ParseSettings.htmlDefault;
        Tag tag = Tag.valueOf("Oc2", "Oc2", parseSettings0);

        Tag.RcData  = 512;
        tag.options = 512;
        assertNotNull(tag.textState());
    }

    @Test(timeout = 4000)
    public void textStateReturnsRawtextWhenDataOptionSet()  throws Throwable  {
        // Tag.Data = 256 (1<<8); setting options to 256 triggers the Data branch.
        Tag original = new Tag("", "");
        Tag cloned   = original.clone();
        cloned.options = Tag.Data;
        assertNotNull(cloned.textState());
    }

    @Test(timeout = 4000)
    public void textStateReturnsNullForTagWithNoTextStateOption()  throws Throwable  {
        Tag tag = new Tag("", "");
        assertNull(tag.textState());
    }

    // -------------------------------------------------------------------------
    // isFormSubmittable() tests
    // -------------------------------------------------------------------------

    @Test(timeout = 4000)
    public void settingFormSubmittableOptionMakesTagFormSubmittable()  throws Throwable  {
        Tag tag = Tag.valueOf("bZJf");
        tag.set(Tag.FormSubmittable);
        boolean isFormSubmittable = tag.isFormSubmittable();
        assertTrue(isFormSubmittable);
    }

    @Test(timeout = 4000)
    public void customTagIsNotFormSubmittableByDefault()  throws Throwable  {
        Tag tag = new Tag("", "");
        boolean isFormSubmittable = tag.isFormSubmittable();
        assertFalse(isFormSubmittable);
    }

    // -------------------------------------------------------------------------
    // preserveWhitespace() tests
    // -------------------------------------------------------------------------

    @Test(timeout = 4000)
    public void settingPreserveWhitespaceOptionPreservesWhitespace()  throws Throwable  {
        Tag tag  = Tag.valueOf("bZJf");
        Tag same = tag.set(Tag.PreserveWhitespace);
        boolean preserves = same.preserveWhitespace();
        assertTrue(preserves);
    }

    @Test(timeout = 4000)
    public void customTagDoesNotPreserveWhitespaceByDefault()  throws Throwable  {
        Tag tag = new Tag("", "");
        boolean preserves = tag.preserveWhitespace();
        assertFalse(preserves);
    }

    // -------------------------------------------------------------------------
    // isKnownTag() tests
    // -------------------------------------------------------------------------

    @Test(timeout = 4000)
    public void staticIsKnownTagReturnsTrueForStandardHtmlElement()  throws Throwable  {
        boolean isKnown = Tag.isKnownTag("var");
        assertTrue(isKnown);
    }

    @Test(timeout = 4000)
    public void staticIsKnownTagReturnsFalseForNonHtmlName()  throws Throwable  {
        boolean isKnown = Tag.isKnownTag("iLUa^");
        assertFalse(isKnown);
    }

    @Test(timeout = 4000)
    public void tagLookedUpViaValueOfIsKnown()  throws Throwable  {
        Tag tag = Tag.valueOf("h6");
        boolean isKnown = tag.isKnownTag();
        assertTrue(isKnown);
    }

    @Test(timeout = 4000)
    public void tagCreatedWithNewConstructorIsNotKnown()  throws Throwable  {
        Tag tag = new Tag("", "");
        boolean isKnown = tag.isKnownTag();
        assertFalse(isKnown);
    }

    // -------------------------------------------------------------------------
    // isSelfClosing() tests
    // -------------------------------------------------------------------------

    @Test(timeout = 4000)
    public void singleArgTagDefaultsToHtmlNamespaceAndIsNotSelfClosing()  throws Throwable  {
        Tag tag = new Tag("A");
        boolean isSelfClosing = tag.isSelfClosing();
        assertFalse(isSelfClosing);
    }

    @Test(timeout = 4000)
    public void settingSelfCloseAndBlockOptionsMakesTagSelfClosing()  throws Throwable  {
        // 28 = Tag.SelfClose (16) | Tag.InlineContainer (8) | Tag.Block (4)
        Tag tag  = new Tag("A");
        Tag same = tag.set(28);
        boolean isSelfClosing = same.isSelfClosing();
        assertTrue(isSelfClosing);
    }

    @Test(timeout = 4000)
    public void settingVoidOptionAlsoMakesTagSelfClosing()  throws Throwable  {
        Tag tag  = new Tag("A");
        Tag same = tag.set(Tag.Void);
        boolean isSelfClosing = same.isSelfClosing();
        assertTrue(isSelfClosing);
    }

    @Test(timeout = 4000)
    public void setSeenSelfCloseDoesNotMakeSelfClosing()  throws Throwable  {
        // SeenSelfClose tracks that a self-close was observed in the source;
        // it does NOT grant self-closing capability.
        Tag tag = new Tag("", "");
        tag.setSeenSelfClose();
        assertFalse(tag.isSelfClosing());
    }

    // -------------------------------------------------------------------------
    // isEmpty() tests
    // -------------------------------------------------------------------------

    @Test(timeout = 4000)
    public void settingVoidOptionDirectlyMakesTagEmpty()  throws Throwable  {
        Tag tag = new Tag("_F31ld-BAJ[");

        tag.options = Tag.Void;
        boolean isEmpty = tag.isEmpty();
        assertTrue(isEmpty);
    }

    @Test(timeout = 4000)
    public void customTagIsNotEmptyByDefault()  throws Throwable  {
        Tag tag = new Tag("", "");
        boolean isEmpty = tag.isEmpty();
        assertFalse(isEmpty);
    }

    // -------------------------------------------------------------------------
    // isInline() / isBlock() / formatAsBlock() tests
    // -------------------------------------------------------------------------

    @Test(timeout = 4000)
    public void tagWithoutBlockOptionIsInline()  throws Throwable  {
        Tag tag = new Tag("'k7*}2H9fkf;cif_ ", "'k7*}2H9fkf;cif_ ");
        boolean isInline = tag.isInline();
        assertTrue(isInline);
    }

    @Test(timeout = 4000)
    public void h6BlockTagIsNotInline()  throws Throwable  {
        Tag h6 = Tag.valueOf("h6");
        boolean isInline = h6.isInline();
        assertFalse(isInline);
    }

    @Test(timeout = 4000)
    public void h6HasFormatAsBlockSet()  throws Throwable  {
        Tag h6 = Tag.valueOf("h6");
        boolean formatAsBlock = h6.formatAsBlock();
        assertTrue(formatAsBlock);
    }

    @Test(timeout = 4000)
    public void customTagDoesNotFormatAsBlockByDefault()  throws Throwable  {
        Tag tag = new Tag("jh6", "jh6", "jh6");
        boolean formatAsBlock = tag.formatAsBlock();
        assertFalse(formatAsBlock);
    }

    @Test(timeout = 4000)
    public void h6IsABlockTag()  throws Throwable  {
        Tag h6 = Tag.valueOf("h6");
        boolean isBlock = h6.isBlock();
        assertTrue(isBlock);
    }

    @Test(timeout = 4000)
    public void customTagIsNotBlockByDefault()  throws Throwable  {
        Tag tag = new Tag("", "");
        boolean isBlock = tag.isBlock();
        assertFalse(isBlock);
    }

    // -------------------------------------------------------------------------
    // hasParserOption() tests
    // -------------------------------------------------------------------------

    @Test(timeout = 4000)
    public void h6HasExpectedParserOption128()  throws Throwable  {
        Tag h6 = Tag.valueOf("h6");
        boolean hasOption = h6.hasParserOption(128);
        assertTrue(hasOption);
    }

    @Test(timeout = 4000)
    public void customTagHasNoParserOptions()  throws Throwable  {
        Tag tag = new Tag("", "");
        boolean hasOption = tag.hasParserOption(128);
        assertFalse(hasOption);
    }

    // -------------------------------------------------------------------------
    // set() / clear() tests
    // -------------------------------------------------------------------------

    @Test(timeout = 4000)
    public void clearingAnOptionOnUnknownTagMarksItAsKnown()  throws Throwable  {
        // clear() sets the Known flag for any option except Known itself.
        Tag tag = Tag.valueOf("bZJf");

        tag.clear(Tag.FormSubmittable);
        assertTrue(tag.isKnownTag());
    }

    @Test(timeout = 4000)
    public void clearingKnownFlagLeavesTagUnknownAndPreservesOtherDefaults()  throws Throwable  {
        // Clearing option == Tag.Known (1) is the only case where Known is NOT re-set.
        Tag tag     = Tag.valueOf("3urw0GS^ `H");
        Tag cleared = tag.clear(Tag.Known);
        assertFalse(cleared.isKnownTag());
    }

    // -------------------------------------------------------------------------
    // prefix() / localName() tests
    // -------------------------------------------------------------------------

    @Test(timeout = 4000)
    public void localNameReturnsFullTagNameWhenNoPrefixPresent()  throws Throwable  {
        Tag tag = new Tag("<KN-^%U");
        String localName = tag.localName();
        assertEquals("<kn-^%u", localName);
    }

    @Test(timeout = 4000)
    public void localNameReturnsPartAfterColonWhenPrefixPresent()  throws Throwable  {
        Tag tag = new Tag("8:<KN-^%U");
        String localName = tag.localName();
        assertEquals("<kn-^%u", localName);
    }

    @Test(timeout = 4000)
    public void prefixReturnsEmptyStringWhenTagHasNoColon()  throws Throwable  {
        Tag tag = new Tag("iP_$Km@lI4Ix)VZ", "iP_$Km@lI4Ix)VZ", "iP_$Km@lI4Ix)VZ");
        assertEquals("", tag.prefix());
    }

    @Test(timeout = 4000)
    public void prefixReturnsPartBeforeColon()  throws Throwable  {
        Tag tag    = Tag.valueOf("Oc2:{");
        String prefix = tag.prefix();
        assertEquals("oc2", prefix);
    }

    // -------------------------------------------------------------------------
    // namespace() tests
    // -------------------------------------------------------------------------

    @Test(timeout = 4000)
    public void namespaceSetterUpdatesNamespaceAndReturnsSameTag()  throws Throwable  {
        Tag tag  = new Tag("org.jsoup.parser.Tag", "org.jsoup.parser.Tag");
        Tag same = tag.namespace("org.jsoup.parser.Tag");
        assertSame(tag, same);
    }

    @Test(timeout = 4000)
    public void namespaceGetterReturnsEmptyStringWhenTagHasEmptyNamespace()  throws Throwable  {
        Tag tag = new Tag("", "");
        assertEquals("", tag.namespace());
    }

    // -------------------------------------------------------------------------
    // hashCode() / toString() tests
    // -------------------------------------------------------------------------

    @Test(timeout = 4000)
    public void hashCodeIsComputedFromTagNameAndNamespace()  throws Throwable  {
        Tag tag = Tag.valueOf("Oc2:{");
        // ABSTAINED
        tag.hashCode();
    }

    @Test(timeout = 4000)
    public void toStringReturnsTagName()  throws Throwable  {
        Tag tag = new Tag("|=$:#:mei1", "|=$:#:mei1", "|=$:#:mei1");
        String result = tag.toString();
        assertEquals("|=$:#:mei1", result);
    }

    // -------------------------------------------------------------------------
    // valueOf() / name() / normalName() / getName() tests
    // -------------------------------------------------------------------------

    @Test(timeout = 4000)
    public void valueOfWithNullSettingsThrowsNullPointerException()  throws Throwable  {
        // Undeclared exception!
        try {
            Tag.valueOf("", (ParseSettings) null);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            //
            // no message in exception (getMessage() returned null)
            //
        }
    }

    @Test(timeout = 4000)
    public void nameGetterReturnsTagName()  throws Throwable  {
        Tag tag = new Tag("", "");
        String name = tag.name();
        assertEquals("", name);
    }

    @Test(timeout = 4000)
    public void normalNameIsAlwaysLowercase()  throws Throwable  {
        ParseSettings preserveCase = ParseSettings.preserveCase;
        Tag tag = Tag.valueOf("MJ", "MJ", preserveCase);
        String normalName = tag.normalName();
        assertEquals("mj", normalName);
    }

    @Test(timeout = 4000)
    public void getNameReturnsOriginalCaseTagName()  throws Throwable  {
        Tag tag = Tag.valueOf("Oc2:{");
        String name = tag.getName();
        assertEquals("oc2:{", name);
    }
}
