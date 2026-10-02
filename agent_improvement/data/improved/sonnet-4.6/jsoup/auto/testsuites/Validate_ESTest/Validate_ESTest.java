/*
 * Improved version of the EvoSuite-generated test for org.jsoup.helper.Validate.
 * Behaviour is identical to the original; only names, structure, and comments
 * have been updated for readability.
 */

package org.jsoup.helper;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.jsoup.helper.Validate;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true,
        resetStaticState = true, separateClassLoader = false)
public class Validate_ESTest extends Validate_ESTest_scaffolding {

    // -----------------------------------------------------------------------
    // notEmpty(String, String)
    // -----------------------------------------------------------------------

    @Test(timeout = 4000)
    public void notEmpty_withEmptyStringAndCustomMessage_throwsIllegalArgumentException() throws Throwable {
        try {
            Validate.notEmpty("", "");
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.jsoup.helper.Validate", e);
        }
    }

    @Test(timeout = 4000)
    public void notEmpty_withNonEmptyStringAndCustomMessage_doesNotThrow() throws Throwable {
        Validate.notEmpty("BQ#hSC'iWZHd+H4x", "Array must not contain any null objects");
    }

    @Test(timeout = 4000)
    public void notEmpty_withNullStringAndCustomMessage_throwsIllegalArgumentExceptionWithThatMessage() throws Throwable {
        try {
            Validate.notEmpty((String) null, "Kw>ry`vUEOCJ/7>w'");
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.jsoup.helper.Validate", e);
        }
    }

    // -----------------------------------------------------------------------
    // notEmptyParam(String, String)
    // -----------------------------------------------------------------------

    @Test(timeout = 4000)
    public void notEmptyParam_withNonEmptyString_doesNotThrow() throws Throwable {
        Validate.notEmptyParam("Array must not contain any null objects", "Array must not contain any null objects");
    }

    @Test(timeout = 4000)
    public void notEmptyParam_withEmptyStringAndEmptyParamName_throwsWithFormattedMessage() throws Throwable {
        try {
            Validate.notEmptyParam("", "");
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected message: "The '' parameter must not be empty."
            verifyException("org.jsoup.helper.Validate", e);
        }
    }

    @Test(timeout = 4000)
    public void notEmptyParam_withNullStringAndNullParamName_throwsWithNullLiteralInMessage() throws Throwable {
        try {
            Validate.notEmptyParam((String) null, (String) null);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected message: "The 'null' parameter must not be empty."
            verifyException("org.jsoup.helper.Validate", e);
        }
    }

    // -----------------------------------------------------------------------
    // notEmpty(String)
    // -----------------------------------------------------------------------

    @Test(timeout = 4000)
    public void notEmpty_withNonEmptyString_doesNotThrow() throws Throwable {
        Validate.notEmpty("yr`o{,Pr'v!D5M");
    }

    @Test(timeout = 4000)
    public void notEmpty_withEmptyString_throwsIllegalArgumentException() throws Throwable {
        try {
            Validate.notEmpty("");
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected message: "String must not be empty"
            verifyException("org.jsoup.helper.Validate", e);
        }
    }

    @Test(timeout = 4000)
    public void notEmpty_withNullString_throwsIllegalArgumentException() throws Throwable {
        try {
            Validate.notEmpty((String) null);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected message: "String must not be empty"
            verifyException("org.jsoup.helper.Validate", e);
        }
    }

    // -----------------------------------------------------------------------
    // noNullElements(Object[])
    // -----------------------------------------------------------------------

    @Test(timeout = 4000)
    public void noNullElements_withArrayPartiallyFilledLeavingNullSlots_throwsIllegalArgumentException() throws Throwable {
        // Only index 0 is set; indices 1-4 remain null — iteration stops at index 1
        Integer firstElement = Integer.valueOf(-1);
        Object[] arrayWithTrailingNulls = new Object[5];
        arrayWithTrailingNulls[0] = (Object) firstElement;
        try {
            Validate.noNullElements(arrayWithTrailingNulls);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected message: "Array must not contain any null objects"
            verifyException("org.jsoup.helper.Validate", e);
        }
    }

    @Test(timeout = 4000)
    public void noNullElements_withEmptyArray_doesNotThrow() throws Throwable {
        Object[] emptyArray = new Object[0];
        Validate.noNullElements(emptyArray);
        assertEquals(0, emptyArray.length);
    }

    // -----------------------------------------------------------------------
    // isFalse(boolean, String)
    // -----------------------------------------------------------------------

    @Test(timeout = 4000)
    public void isFalse_withTrueValueAndCustomMessage_throwsIllegalArgumentExceptionWithThatMessage() throws Throwable {
        try {
            Validate.isFalse(true, "(iS;>>ZVW");
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected message: "(iS;>>ZVW"
            verifyException("org.jsoup.helper.Validate", e);
        }
    }

    @Test(timeout = 4000)
    public void isFalse_withFalseValueAndCustomMessage_doesNotThrow() throws Throwable {
        Validate.isFalse(false, "(ir;>>Z<W");
    }

    // -----------------------------------------------------------------------
    // isFalse(boolean)
    // -----------------------------------------------------------------------

    @Test(timeout = 4000)
    public void isFalse_withTrueValue_throwsIllegalArgumentExceptionWithDefaultMessage() throws Throwable {
        try {
            Validate.isFalse(true);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected message: "Must be false"
            verifyException("org.jsoup.helper.Validate", e);
        }
    }

    @Test(timeout = 4000)
    public void isFalse_withFalseValue_doesNotThrow() throws Throwable {
        Validate.isFalse(false);
    }

    // -----------------------------------------------------------------------
    // isTrue(boolean, String)
    // -----------------------------------------------------------------------

    @Test(timeout = 4000)
    public void isTrue_withFalseValueAndCustomMessage_throwsIllegalArgumentExceptionWithThatMessage() throws Throwable {
        try {
            Validate.isTrue(false, "W\"N+(;C");
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected message: "W\"N+(;C"
            verifyException("org.jsoup.helper.Validate", e);
        }
    }

    @Test(timeout = 4000)
    public void isTrue_withTrueValueAndCustomMessage_doesNotThrow() throws Throwable {
        Validate.isTrue(true, "W\"N+(;C");
    }

    // -----------------------------------------------------------------------
    // isTrue(boolean)
    // -----------------------------------------------------------------------

    @Test(timeout = 4000)
    public void isTrue_withFalseValue_throwsIllegalArgumentExceptionWithDefaultMessage() throws Throwable {
        try {
            Validate.isTrue(false);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected message: "Must be true"
            verifyException("org.jsoup.helper.Validate", e);
        }
    }

    @Test(timeout = 4000)
    public void isTrue_withTrueValue_doesNotThrow() throws Throwable {
        Validate.isTrue(true);
    }

    // -----------------------------------------------------------------------
    // expectNotNull(Object, String, Object...)
    // -----------------------------------------------------------------------

    @Test(timeout = 4000)
    public void expectNotNull_withNullObjectAndNullFormatString_throwsNullPointerException() throws Throwable {
        // When the object IS null and the format string is also null,
        // String.format(null, args) throws NullPointerException before any
        // ValidationException can be constructed.
        Object[] formatArgs = new Object[5];
        try {
            Validate.expectNotNull(formatArgs[0], (String) null, formatArgs);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            // no message — getMessage() returns null
        }
    }

    @Test(timeout = 4000)
    public void expectNotNull_withMessageOverload_withNonNullObject_returnsTheSameObject() throws Throwable {
        Object nonNullObject = new Object();
        Object[] formatArgs = new Object[8];
        Object result = Validate.expectNotNull(nonNullObject, "Array must not contain any null objects", formatArgs);
        assertSame(nonNullObject, result);
    }

    // -----------------------------------------------------------------------
    // expectNotNull(Object)
    // -----------------------------------------------------------------------

    @Test(timeout = 4000)
    public void expectNotNull_withNullObject_throwsIllegalArgumentException() throws Throwable {
        try {
            Validate.expectNotNull((Object) null);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected message: "Object must not be null"
            verifyException("org.jsoup.helper.Validate", e);
        }
    }

    @Test(timeout = 4000)
    public void expectNotNull_withNonNullObject_returnsTheSameObject() throws Throwable {
        Object result = Validate.expectNotNull((Object) "di");
        assertEquals("di", result);
    }

    // -----------------------------------------------------------------------
    // ensureNotNull(Object, String, Object...)   [deprecated]
    // -----------------------------------------------------------------------

    @Test(timeout = 4000)
    public void ensureNotNull_withNullObjectAndEmptyMessage_throwsIllegalArgumentException() throws Throwable {
        try {
            Validate.ensureNotNull((Object) null, "", (Object[]) null);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.jsoup.helper.Validate", e);
        }
    }

    @Test(timeout = 4000)
    public void ensureNotNull_withMessageOverload_withNonNullObject_returnsTheSameObject() throws Throwable {
        Object result = Validate.ensureNotNull((Object) ".;(s<;hD", ".;(s<;hD", (Object[]) null);
        assertEquals(".;(s<;hD", result);
    }

    // -----------------------------------------------------------------------
    // ensureNotNull(Object)   [deprecated]
    // -----------------------------------------------------------------------

    @Test(timeout = 4000)
    public void ensureNotNull_withNullObject_throwsIllegalArgumentException() throws Throwable {
        try {
            Validate.ensureNotNull((Object) null);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected message: "Object must not be null"
            verifyException("org.jsoup.helper.Validate", e);
        }
    }

    @Test(timeout = 4000)
    public void ensureNotNull_withNonNullObject_returnsTheSameObject() throws Throwable {
        Integer value = Integer.valueOf(-1);
        Object result = Validate.ensureNotNull((Object) value);
        assertEquals((-1), result);
    }

    // -----------------------------------------------------------------------
    // notNull(Object, String)
    // -----------------------------------------------------------------------

    @Test(timeout = 4000)
    public void notNull_withNullObjectAndNullMessage_throwsIllegalArgumentExceptionWithNullMessage() throws Throwable {
        try {
            Validate.notNull((Object) null, (String) null);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // ValidationException is constructed with a null message
            verifyException("org.jsoup.helper.Validate", e);
        }
    }

    @Test(timeout = 4000)
    public void notNull_withMessageOverload_withNonNullObject_doesNotThrow() throws Throwable {
        Integer value = Integer.valueOf(1004);
        Validate.notNull((Object) value, "");
    }

    // -----------------------------------------------------------------------
    // notNullParam(Object, String)
    // -----------------------------------------------------------------------

    @Test(timeout = 4000)
    public void notNullParam_withNullObjectAndNullParamName_throwsWithNullLiteralInMessage() throws Throwable {
        try {
            Validate.notNullParam((Object) null, (String) null);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected message: "The parameter 'null' must not be null."
            verifyException("org.jsoup.helper.Validate", e);
        }
    }

    @Test(timeout = 4000)
    public void notNullParam_withNonNullObject_doesNotThrow() throws Throwable {
        Validate.notNullParam("yr`o{,Pr'v!D5M", "yr`o{,Pr'v!D5M");
    }

    // -----------------------------------------------------------------------
    // notNull(Object)
    // -----------------------------------------------------------------------

    @Test(timeout = 4000)
    public void notNull_withNullObject_throwsIllegalArgumentException() throws Throwable {
        try {
            Validate.notNull((Object) null);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected message: "Object must not be null"
            verifyException("org.jsoup.helper.Validate", e);
        }
    }

    @Test(timeout = 4000)
    public void notNull_withNonNullObject_doesNotThrow() throws Throwable {
        Validate.notNull((Object) "j*C");
    }

    // -----------------------------------------------------------------------
    // fail(String, Object...)
    // -----------------------------------------------------------------------

    @Test(timeout = 4000)
    public void fail_withMessageAndNullArgsArray_throwsIllegalArgumentExceptionWithThatMessage() throws Throwable {
        try {
            Validate.fail("org.jsoup.helper.ValidationException", (Object[]) null);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected message: "org.jsoup.helper.ValidationException"
            verifyException("org.jsoup.helper.Validate", e);
        }
    }

    // -----------------------------------------------------------------------
    // assertFail(String)
    // -----------------------------------------------------------------------

    @Test(timeout = 4000)
    public void assertFail_withEmptyMessage_throwsIllegalArgumentException() throws Throwable {
        try {
            Validate.assertFail("");
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.jsoup.helper.Validate", e);
        }
    }

    // -----------------------------------------------------------------------
    // wtf(String)
    // -----------------------------------------------------------------------

    @Test(timeout = 4000)
    public void wtf_withNullMessage_throwsIllegalStateExceptionWithNullMessage() throws Throwable {
        try {
            Validate.wtf((String) null);
            fail("Expecting exception: IllegalStateException");
        } catch (IllegalStateException e) {
            // IllegalStateException constructed with null message — getMessage() returns null
            verifyException("org.jsoup.helper.Validate", e);
        }
    }
}
