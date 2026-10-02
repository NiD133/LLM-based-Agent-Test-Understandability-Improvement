/*
 * Improved version of the EvoSuite-generated test for OptionGroup.
 * Behaviour is identical to the original; only names and structure were changed
 * to improve readability.
 */

package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.Collection;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionGroup;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true,
        resetStaticState = true, separateClassLoader = false)
public class OptionGroup_ESTest extends OptionGroup_ESTest_scaffolding {

    // -----------------------------------------------------------------------
    // toString() tests
    // -----------------------------------------------------------------------

    /**
     * Two options with null short-opt (rendered as long-opt with "--" prefix).
     * The first has no description; the second has a long-opt of "[]".
     * Expected: "[--null, --[]]"
     */
    @Test(timeout = 4000)
    public void test00_toString_twoNullShortOptOptions_renderedAsLongOpts() throws Throwable {
        OptionGroup group = new OptionGroup();
        Option optionWithNullOpt = new Option((String) null, (String) null);
        group.addOption(optionWithNullOpt);
        Option optionWithBracketLongOpt = new Option((String) null, "[]", true, (String) null);
        group.addOption(optionWithBracketLongOpt);

        String result = group.toString();
        assertEquals("[--null, --[]]", result);
    }

    /**
     * One option with null short-opt and an empty (non-null) description.
     * The description is appended after a space in the output.
     * Expected: "[--null ]"
     */
    @Test(timeout = 4000)
    public void test01_toString_optionWithEmptyDescription_descriptionAppendedAfterSpace() throws Throwable {
        OptionGroup group = new OptionGroup();
        Option option = new Option((String) null, (String) null);
        group.addOption(option);
        option.setDescription("");

        String result = group.toString();
        assertEquals("[--null ]", result);
    }

    /**
     * One option that has a short-opt ("tkgJ") and no long-opt.
     * Short-opts are rendered with a single "-" prefix.
     * Expected: "[-tkgJ]"
     */
    @Test(timeout = 4000)
    public void test02_toString_optionWithShortOpt_renderedWithSingleDashPrefix() throws Throwable {
        OptionGroup group = new OptionGroup();
        Option option = new Option("tkgJ", (String) null);
        group.addOption(option);

        String result = group.toString();
        assertEquals("[-tkgJ]", result);
    }

    // -----------------------------------------------------------------------
    // setSelected() tests
    // -----------------------------------------------------------------------

    /**
     * Selecting a different option after one has already been selected must
     * throw AlreadySelectedException (or its supertype Exception), because
     * group members are mutually exclusive.
     */
    @Test(timeout = 4000)
    public void test03_setSelected_differentOptionWhenOneAlreadySelected_throwsException() throws Throwable {
        OptionGroup group = new OptionGroup();
        Option firstSelected = new Option((String) null, "[]", true, (String) null);
        Option conflictingOption = new Option((String) null, (String) null);
        group.setSelected(firstSelected);

        try {
            group.setSelected(conflictingOption);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            //
            // The option 'null' was specified but an option from this group has already been selected: '[]'
            //
        }
    }

    /**
     * Re-selecting the same option that is already selected must succeed
     * (no exception thrown). Verifies idempotent behaviour.
     */
    @Test(timeout = 4000)
    public void test04_setSelected_sameOptionSelectedTwice_succeeds() throws Throwable {
        OptionGroup group = new OptionGroup();
        Option option = new Option((String) null, "[]", true, (String) null);
        group.setSelected(option);
        group.setSelected(option);   // second call must not throw

    }

    /**
     * Passing null to setSelected() resets the selection.
     * The group must report not required after this operation.
     */
    @Test(timeout = 4000)
    public void test05_setSelected_nullOption_resetsSelection() throws Throwable {
        OptionGroup group = new OptionGroup();
        group.setSelected((Option) null);
        assertFalse(group.isSelected());
    }

    // -----------------------------------------------------------------------
    // isSelected() tests
    // -----------------------------------------------------------------------

    /**
     * After an option has been selected, isSelected() must return true.
     */
    @Test(timeout = 4000)
    public void test06_isSelected_afterOptionIsSelected_returnsTrue() throws Throwable {
        OptionGroup group = new OptionGroup();
        Option option = new Option((String) null, "", false, (String) null);
        group.setSelected(option);

        boolean selected = group.isSelected();
        assertTrue(selected);
    }

    /**
     * On a freshly constructed group with no selection, isSelected() must return false.
     */
    @Test(timeout = 4000)
    public void test07_isSelected_noOptionSelected_returnsFalse() throws Throwable {
        OptionGroup group = new OptionGroup();

        boolean selected = group.isSelected();
        assertFalse(selected);
    }

    // -----------------------------------------------------------------------
    // getNames() tests
    // -----------------------------------------------------------------------

    /**
     * getNames() must return a non-null collection even when the group is empty.
     */
    @Test(timeout = 4000)
    public void test08_getNames_emptyGroup_returnsNonNullCollection() throws Throwable {
        OptionGroup group = new OptionGroup();

        Collection<String> names = group.getNames();
        assertNotNull(names);
    }

    // -----------------------------------------------------------------------
    // isRequired() / setRequired() tests
    // -----------------------------------------------------------------------

    /**
     * After explicitly setting required to false, isRequired() must return false.
     */
    @Test(timeout = 4000)
    public void test09_setRequired_false_isRequiredReturnsFalse() throws Throwable {
        OptionGroup group = new OptionGroup();
        group.setRequired(false);
        assertFalse(group.isRequired());
    }

    /**
     * A newly constructed OptionGroup must not be required by default.
     */
    @Test(timeout = 4000)
    public void test10_isRequired_defaultGroup_returnsFalse() throws Throwable {
        OptionGroup group = new OptionGroup();

        boolean required = group.isRequired();
        assertFalse(required);
    }
}
