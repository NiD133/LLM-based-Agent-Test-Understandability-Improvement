/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link JavaVersion}.
 */
class JavaVersionTest extends AbstractLangTest {

    /**
     * Asserts that {@link JavaVersion#get(String)} maps the given version string to the expected enum constant.
     *
     * @param expected      the constant {@code get} is expected to return.
     * @param versionString the version string to look up.
     */
    private static void assertGetMapsTo(final JavaVersion expected, final String versionString) {
        assertEquals(expected, JavaVersion.get(versionString),
                "get(\"" + versionString + "\") should map to " + expected);
    }

    @Test
    void testAtLeast() {
        // A lower version is never "at least" a higher one.
        assertFalse(JavaVersion.JAVA_1_2.atLeast(JavaVersion.JAVA_1_5), "1.2 should not be at least 1.5");
        assertFalse(JavaVersion.JAVA_1_6.atLeast(JavaVersion.JAVA_1_7), "1.6 should not be at least 1.7");

        // A higher version is always "at least" a lower one.
        assertTrue(JavaVersion.JAVA_1_5.atLeast(JavaVersion.JAVA_1_2), "1.5 should be at least 1.2");

        // JAVA_0_9 (Android) is internally weighted as 1.5f, so it ranks at the 1.5 level.
        assertTrue(JavaVersion.JAVA_0_9.atLeast(JavaVersion.JAVA_1_5), "0.9 should be at least 1.5");
        assertFalse(JavaVersion.JAVA_0_9.atLeast(JavaVersion.JAVA_1_6), "0.9 should not be at least 1.6");
    }

    @Test
    void testGetJavaVersion() {
        // Legacy "1.x" names.
        assertGetMapsTo(JavaVersion.JAVA_0_9, "0.9");
        assertGetMapsTo(JavaVersion.JAVA_1_1, "1.1");
        assertGetMapsTo(JavaVersion.JAVA_1_2, "1.2");
        assertGetMapsTo(JavaVersion.JAVA_1_3, "1.3");
        assertGetMapsTo(JavaVersion.JAVA_1_4, "1.4");
        assertGetMapsTo(JavaVersion.JAVA_1_5, "1.5");
        assertGetMapsTo(JavaVersion.JAVA_1_6, "1.6");
        assertGetMapsTo(JavaVersion.JAVA_1_7, "1.7");
        assertGetMapsTo(JavaVersion.JAVA_1_8, "1.8");

        // Modern single-number names.
        assertGetMapsTo(JavaVersion.JAVA_9, "9");
        assertGetMapsTo(JavaVersion.JAVA_10, "10");
        assertGetMapsTo(JavaVersion.JAVA_11, "11");
        assertGetMapsTo(JavaVersion.JAVA_12, "12");
        assertGetMapsTo(JavaVersion.JAVA_13, "13");
        assertGetMapsTo(JavaVersion.JAVA_14, "14");
        assertGetMapsTo(JavaVersion.JAVA_15, "15");
        assertGetMapsTo(JavaVersion.JAVA_16, "16");
        assertGetMapsTo(JavaVersion.JAVA_17, "17");
        assertGetMapsTo(JavaVersion.JAVA_18, "18");
        assertGetMapsTo(JavaVersion.JAVA_19, "19");
        assertGetMapsTo(JavaVersion.JAVA_20, "20");
        assertGetMapsTo(JavaVersion.JAVA_21, "21");
        assertGetMapsTo(JavaVersion.JAVA_22, "22");
        assertGetMapsTo(JavaVersion.JAVA_23, "23");
        assertGetMapsTo(JavaVersion.JAVA_24, "24");
        assertGetMapsTo(JavaVersion.JAVA_25, "25");
        assertGetMapsTo(JavaVersion.JAVA_26, "26");
        assertGetMapsTo(JavaVersion.JAVA_27, "27");

        // Unknown-but-newer versions fall back to JAVA_RECENT rather than null.
        assertGetMapsTo(JavaVersion.JAVA_RECENT, "1.10"); // decimals greater than .9
        assertGetMapsTo(JavaVersion.JAVA_RECENT, "99");   // LANG-1384: unhandled high version

        // getJavaVersion is a thin wrapper that must agree with get.
        assertEquals(JavaVersion.get("1.5"), JavaVersion.getJavaVersion("1.5"), "Wrapper method failed");
    }

    @Test
    void testToString() {
        // toString returns the standard name passed to the constructor.
        assertEquals("1.2", JavaVersion.JAVA_1_2.toString());
    }

}
