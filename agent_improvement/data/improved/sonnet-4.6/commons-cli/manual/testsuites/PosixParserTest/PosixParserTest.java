/*
  Licensed to the Apache Software Foundation (ASF) under one or more
  contributor license agreements.  See the NOTICE file distributed with
  this work for additional information regarding copyright ownership.
  The ASF licenses this file to You under the Apache License, Version 2.0
  (the "License"); you may not use this file except in compliance with
  the License.  You may obtain a copy of the License at

      https://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
 */

package org.apache.commons.cli;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Tests for the deprecated {@link PosixParser}.
 *
 * <p>PosixParser inherits most tests from {@link AbstractParserTestCase}. A subset of those tests
 * are disabled here because PosixParser intentionally does not support certain features:
 * <ul>
 *   <li><b>Single-dash long options</b> ({@code -foo}, {@code -foo=bar}): the PosixParser
 *       treats a leading {@code -} followed by more than one character as a burst of short
 *       options rather than a long-option alias.</li>
 *   <li><b>Short option with {@code =} separator</b> ({@code -f=bar}): the {@code =} is not
 *       treated as an argument separator when a single dash is used.</li>
 *   <li><b>Double-dash argument stopping ({@code --}) with a missing option argument</b>:
 *       PosixParser does not raise {@link MissingArgumentException} in this scenario.</li>
 *   <li><b>Negative number as a recognized option</b> (CLI-184): a leading {@code -} followed
 *       by digits is ambiguous in POSIX mode and is not handled as an option flag.</li>
 *   <li><b>Ambiguous single-dash partial long options with {@code =}</b>: burst-mode parsing
 *       does not perform prefix matching on long-option names.</li>
 * </ul>
 *
 * <p>Use {@link DefaultParser} for full support of all these features.
 *
 * TODO Needs a rework using JUnit parameterized tests.
 */
class PosixParserTest extends AbstractParserTestCase {

    /**
     * Creates a {@link PosixParser} instance for each test.
     * {@code @SuppressWarnings("deprecation")} is required because {@link PosixParser} itself
     * is deprecated in favour of {@link DefaultParser}.
     */
    @Override
    @SuppressWarnings("deprecation")
    @BeforeEach
    public void setUp() {
        super.setUp();
        parser = new PosixParser();
    }

    // -------------------------------------------------------------------------
    // Single-dash long options (-foo, -foo=bar) are not supported: PosixParser
    // bursts the characters after '-' as individual short options instead of
    // matching them against long-option names.
    // -------------------------------------------------------------------------

    @Override
    @Test
    @Disabled("not supported by the PosixParser")
    void testAmbiguousLongWithoutEqualSingleDash() throws Exception {
    }

    @Override
    @Test
    @Disabled("not supported by the PosixParser")
    void testAmbiguousLongWithoutEqualSingleDash2() throws Exception {
    }

    @Override
    @Test
    @Disabled("not supported by the PosixParser")
    void testAmbiguousPartialLongOption4() throws Exception {
    }

    @Override
    @Test
    @Disabled("not supported by the PosixParser")
    void testLongWithEqualSingleDash() throws Exception {
    }

    @Override
    @Test
    @Disabled("not supported by the PosixParser")
    void testLongWithoutEqualSingleDash() throws Exception {
    }

    @Override
    @Test
    @Disabled("not supported by the PosixParser")
    void testUnambiguousPartialLongOption4() throws Exception {
    }

    // -------------------------------------------------------------------------
    // Short option with '=' separator (-f=bar) is not supported: PosixParser
    // does not recognise '=' as an argument delimiter in burst mode.
    // -------------------------------------------------------------------------

    @Override
    @Test
    @Disabled("not supported by the PosixParser")
    void testShortWithEqual() throws Exception {
    }

    // -------------------------------------------------------------------------
    // '--' handling edge case: PosixParser does not throw MissingArgumentException
    // when '--' appears where a required option argument is expected.
    // -------------------------------------------------------------------------

    @Override
    @Test
    @Disabled("not supported by the PosixParser")
    void testDoubleDash2() throws Exception {
    }

    // -------------------------------------------------------------------------
    // Negative numbers as option flags (CLI-184): '-1' is ambiguous in POSIX
    // burst mode and is not treated as an option name.
    // -------------------------------------------------------------------------

    @Override
    @Test
    @Disabled("not supported by the PosixParser (CLI-184)")
    void testNegativeOption() throws Exception {
    }

    // -------------------------------------------------------------------------
    // Unexpected long-option arguments with a double-dash prefix are not flagged
    // correctly when parsed via single-dash burst mode.
    // -------------------------------------------------------------------------

    @Override
    @Test
    @Disabled("not supported by the PosixParser")
    void testLongWithUnexpectedArgument1() throws Exception {
    }
}
