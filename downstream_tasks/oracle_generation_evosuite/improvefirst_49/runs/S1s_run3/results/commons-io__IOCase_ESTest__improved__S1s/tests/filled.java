/*
 * Improved version of the EvoSuite-generated test for IOCase.
 * Original generated: Mon Jun 15 02:02:50 GMT 2026
 */

package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.apache.commons.io.IOCase;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class IOCase_ESTest extends IOCase_ESTest_scaffolding {

    // -----------------------------------------------------------------------
    // checkStartsWith
    // -----------------------------------------------------------------------

    @Test(timeout = 4000)
    public void checkStartsWith_systemCase_nonMatchingStrings_returnsFalse() throws Throwable {
        IOCase systemCase = IOCase.SYSTEM;
        boolean result = systemCase.checkStartsWith("zmIl1;deJ|AOW", " without breaking the first codepoint oV grapheme;c!8-T\r");
        assertFalse(result);
    }

    @Test(timeout = 4000)
    public void checkStartsWith_insensitiveCase_identicalStrings_returnsTrue() throws Throwable {
        IOCase insensitiveCase = IOCase.INSENSITIVE;
        boolean result = insensitiveCase.checkStartsWith("org.apache.commons.io.Filena+eUtils", "org.apache.commons.io.Filena+eUtils");
        assertTrue(result);
    }

    @Test(timeout = 4000)
    public void checkStartsWith_sensitiveCase_emptyStrWithNullStart_returnsFalse() throws Throwable {
        IOCase sensitiveCase = IOCase.SENSITIVE;
        boolean result = sensitiveCase.checkStartsWith("", (String) null);
        assertFalse(result);
    }

    @Test(timeout = 4000)
    public void checkStartsWith_sensitiveCase_bothNull_returnsFalse() throws Throwable {
        IOCase sensitiveCase = IOCase.SENSITIVE;
        boolean result = sensitiveCase.checkStartsWith((String) null, (String) null);
        assertFalse(result);
    }

    // -----------------------------------------------------------------------
    // checkRegionMatches
    // -----------------------------------------------------------------------

    @Test(timeout = 4000)
    public void checkRegionMatches_sensitiveCase_nullSearch_returnsFalse() throws Throwable {
        IOCase sensitiveCase = IOCase.SENSITIVE;
        boolean result = sensitiveCase.checkRegionMatches(">oH(kNS8W#e/$", (-639), (String) null);
        assertFalse(result);
    }

    @Test(timeout = 4000)
    public void checkRegionMatches_insensitiveCase_bothNull_returnsFalse() throws Throwable {
        IOCase insensitiveCase = IOCase.INSENSITIVE;
        boolean result = insensitiveCase.checkRegionMatches((String) null, (-2368), (String) null);
        assertFalse(result);
    }

    // -----------------------------------------------------------------------
    // checkIndexOf
    // -----------------------------------------------------------------------

    @Test(timeout = 4000)
    public void checkIndexOf_insensitiveCase_searchNotPresent_returnsMinusOne() throws Throwable {
        IOCase insensitiveCase = IOCase.INSENSITIVE;
        int index = insensitiveCase.checkIndexOf(" without breaking the first codepoint or grapheme cluster", 15, "!8-T");
        assertEquals(-1, index);
    }

    @Test(timeout = 4000)
    public void checkIndexOf_systemCase_negativeStartIndex_findsMatchAtZero() throws Throwable {
        IOCase systemCase = IOCase.SYSTEM;
        int index = systemCase.checkIndexOf("LINUX", (-1117), "LINUX");
        assertEquals(0, index);
    }

    @Test(timeout = 4000)
    public void checkIndexOf_systemCase_nullSearch_returnsMinusOne() throws Throwable {
        IOCase systemCase = IOCase.SYSTEM;
        int index = systemCase.checkIndexOf("System", 24, (String) null);
        assertEquals(-1, index);
    }

    @Test(timeout = 4000)
    public void checkIndexOf_systemCase_startIndexPastEnd_returnsMinusOne() throws Throwable {
        IOCase systemCase = IOCase.SYSTEM;
        int index = systemCase.checkIndexOf("System", 28, "rB&T\tpqZ\"");
        assertEquals(-1, index);
    }

    @Test(timeout = 4000)
    public void checkIndexOf_insensitiveCase_bothNull_returnsMinusOne() throws Throwable {
        IOCase insensitiveCase = IOCase.INSENSITIVE;
        int index = insensitiveCase.checkIndexOf((String) null, 746, (String) null);
        assertEquals(-1, index);
    }

    // -----------------------------------------------------------------------
    // checkEquals
    // -----------------------------------------------------------------------

    @Test(timeout = 4000)
    public void checkEquals_insensitiveCase_differentStrings_returnsFalse() throws Throwable {
        IOCase insensitiveCase = IOCase.INSENSITIVE;
        boolean result = insensitiveCase.checkEquals("%Te;M@?B_m,ru(g&", "$VALUES");
        assertFalse(result);
    }

    @Test(timeout = 4000)
    public void checkEquals_insensitiveCase_sameValueDifferentCase_returnsTrue() throws Throwable {
        IOCase insensitiveCase = IOCase.INSENSITIVE;
        boolean result = insensitiveCase.checkEquals("com6", "COM6");
        assertTrue(result);
    }

    @Test(timeout = 4000)
    public void checkEquals_systemCase_differentStrings_returnsFalse() throws Throwable {
        IOCase systemCase = IOCase.SYSTEM;
        boolean result = systemCase.checkEquals("zmIl1;deJ|AOW", "^J# C/>cH!\"$");
        assertFalse(result);
    }

    @Test(timeout = 4000)
    public void checkEquals_systemCase_nullFirstArg_returnsFalse() throws Throwable {
        IOCase systemCase = IOCase.SYSTEM;
        boolean result = systemCase.checkEquals((String) null, "phuL");
        assertFalse(result);
    }

    @Test(timeout = 4000)
    public void checkEquals_insensitiveCase_identicalStrings_returnsTrue() throws Throwable {
        IOCase insensitiveCase = IOCase.INSENSITIVE;
        boolean result = insensitiveCase.checkEquals("Hh}^", "Hh}^");
        assertTrue(result);
    }

    // -----------------------------------------------------------------------
    // checkEndsWith
    // -----------------------------------------------------------------------

    @Test(timeout = 4000)
    public void checkEndsWith_insensitiveCase_suffixNotPresent_returnsFalse() throws Throwable {
        IOCase insensitiveCase = IOCase.INSENSITIVE;
        boolean result = insensitiveCase.checkEndsWith("System", "z:=F{9w=V$70~Oy");
        assertFalse(result);
    }

    @Test(timeout = 4000)
    public void checkEndsWith_systemCase_nullSuffix_returnsFalse() throws Throwable {
        IOCase systemCase = IOCase.SYSTEM;
        boolean result = systemCase.checkEndsWith("%pe;MQhIB_m,ru(g&", (String) null);
        assertFalse(result);
    }

    @Test(timeout = 4000)
    public void checkEndsWith_systemCase_suffixNotPresent_returnsFalse() throws Throwable {
        IOCase systemCase = IOCase.SYSTEM;
        boolean result = systemCase.checkEndsWith("System", "z:=F{9w=V$70~Oy");
        assertFalse(result);
    }

    @Test(timeout = 4000)
    public void checkEndsWith_sensitiveCase_bothNull_returnsFalse() throws Throwable {
        IOCase sensitiveCase = IOCase.SENSITIVE;
        boolean result = sensitiveCase.checkEndsWith((String) null, (String) null);
        assertFalse(result);
    }

    // -----------------------------------------------------------------------
    // checkCompareTo
    // -----------------------------------------------------------------------

    @Test(timeout = 4000)
    public void checkCompareTo_systemCase_equalStrings_returnsZero() throws Throwable {
        IOCase systemCase = IOCase.SYSTEM;
        int result = systemCase.checkCompareTo("org.apache.commons.io.Filena+eUtils", "org.apache.commons.io.Filena+eUtils");
        assertEquals(0, result);
    }

    @Test(timeout = 4000)
    public void checkCompareTo_insensitiveCaseFromValue_equalStrings_returnsZero() throws Throwable {
        IOCase systemCase = IOCase.SYSTEM;
        IOCase insensitiveCase = IOCase.INSENSITIVE;
        // value(non-null, default) returns the non-null value
        IOCase resolvedCase = IOCase.value(insensitiveCase, systemCase);

        assertEquals(0, resolvedCase.checkCompareTo("org.apache.commons.io.Filena+eUtils", "org.apache.commons.io.Filena+eUtils"));
    }

    // -----------------------------------------------------------------------
    // value (null-safe factory)
    // -----------------------------------------------------------------------

    @Test(timeout = 4000)
    public void value_nullFirstArg_returnsDefaultValue() throws Throwable {
        IOCase systemCase = IOCase.SYSTEM;
        IOCase result = IOCase.value((IOCase) null, systemCase);
        assertSame(systemCase, result);
    }

    @Test(timeout = 4000)
    public void value_nonNullFirstArg_returnsFirstArg() throws Throwable {
        IOCase insensitiveCase = IOCase.INSENSITIVE;
        IOCase sensitiveCase = IOCase.SENSITIVE;
        IOCase result = IOCase.value(sensitiveCase, insensitiveCase);
        assertSame(sensitiveCase, result);

        boolean isSensitive = IOCase.isCaseSensitive(result);
        assertTrue(isSensitive);
    }

    // -----------------------------------------------------------------------
    // isCaseSensitive (static helper)
    // -----------------------------------------------------------------------

    @Test(timeout = 4000)
    public void isCaseSensitive_nullArg_returnsFalse() throws Throwable {
        boolean result = IOCase.isCaseSensitive((IOCase) null);
        assertFalse(result);
    }

    @Test(timeout = 4000)
    public void isCaseSensitive_insensitiveCase_returnsFalse() throws Throwable {
        IOCase insensitiveCase = IOCase.INSENSITIVE;
        boolean result = IOCase.isCaseSensitive(insensitiveCase);
        assertFalse(result);
    }

    // -----------------------------------------------------------------------
    // forName
    // -----------------------------------------------------------------------

    @Test(timeout = 4000)
    public void forName_unknownName_throwsIllegalArgumentException() throws Throwable {
        // Undeclared exception!
        try {
            IOCase.forName("$VALUES");
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // Illegal IOCase name: $VALUES
            //
        }
    }

    // -----------------------------------------------------------------------
    // toString
    // -----------------------------------------------------------------------

    @Test(timeout = 4000)
    public void toString_insensitiveCase_returnsInsensitiveLabel() throws Throwable {
        IOCase insensitiveCase = IOCase.INSENSITIVE;
        String label = insensitiveCase.toString();
        assertEquals("Insensitive", label);
    }
}
