/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.codec.digest;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

/**
 * Tests {@link Md5Crypt}, the libc crypt() "$1$" MD5-based hash algorithm.
 *
 * <p>Many cases drive the algorithm through {@link Crypt#crypt} because that entry point lets us pin
 * an exact salt and therefore assert against a fixed, reproducible hash string.</p>
 */
@Timeout(3) // Try to avoid occasional hang when testing
class Md5CryptTest {

    /**
     * Shape of every "$1$" hash: the "$1$" prefix, up to 8 salt characters, a "$", then the hash body.
     */
    private static final String MD5_CRYPT_HASH_PATTERN = "^\\$1\\$[a-zA-Z0-9./]{0,8}\\$.{1,}$";

    @Test
    void testCtorDeprecated() {
        assertNotNull(new Md5Crypt());
    }

    @Test
    void testInvalidPrefix() {
        // A salt that is too long, combined with a prefix that is not a valid "$...$" marker, is rejected.
        final byte[] keyBytes = { 1, 2, 3, 4, 5 };
        final String oversizedSaltEndingInBang =
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa!";
        final String oversizedSalt =
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";

        assertThrows(IllegalArgumentException.class,
                () -> Md5Crypt.md5Crypt(keyBytes, oversizedSaltEndingInBang, "(.*a){10000}"));
        assertThrows(IllegalArgumentException.class,
                () -> Md5Crypt.md5Crypt(keyBytes, oversizedSaltEndingInBang, "$(.*a){10000}$"));
        assertThrows(IllegalArgumentException.class,
                () -> Md5Crypt.md5Crypt(keyBytes, oversizedSalt, "$(.*a){10000}$"));
    }

    @Test
    void testMd5CryptBytes() {
        // An empty byte array hashes the same as an empty String.
        assertEquals("$1$foo$9mS5ExwgIECGE5YKlD5o91", Crypt.crypt(new byte[0], "$1$foo"));
        // UTF-8 stores ä ("a with dieresis") as the two bytes 0xc3 0xa4.
        assertEquals("$1$./$52agTEQZs877L9jyJnCNZ1", Crypt.crypt("täst", "$1$./$"));
        // ISO-8859-1 stores "a with dieresis" as the single byte 0xe4, yielding a different hash.
        assertEquals("$1$./$J2UbKzGe0Cpe63WZAt6p//",
                Crypt.crypt("täst".getBytes(StandardCharsets.ISO_8859_1), "$1$./$"));
    }

    @Test
    void testMd5CryptExplicitCall() {
        // With no salt (or an explicitly null salt) a random salt is generated, but the output still matches the format.
        assertTrue(Md5Crypt.md5Crypt("secret".getBytes()).matches(MD5_CRYPT_HASH_PATTERN));
        assertTrue(Md5Crypt.md5Crypt("secret".getBytes(), (String) null).matches(MD5_CRYPT_HASH_PATTERN));
    }

    @Test
    void testMd5CryptExplicitCallWithThreadLocalRandom() {
        // A caller-supplied Random source produces a valid hash just like the default SecureRandom.
        final ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
        assertTrue(Md5Crypt.md5Crypt("secret".getBytes(), threadLocalRandom).matches(MD5_CRYPT_HASH_PATTERN));
        assertTrue(Md5Crypt.md5Crypt("secret".getBytes(), (String) null).matches(MD5_CRYPT_HASH_PATTERN));
    }

    @Test
    void testMd5CryptLongInput() {
        // Plaintext longer than one MD5 block still hashes correctly against a fixed salt.
        assertEquals("$1$1234$MoxekaNNUgfPRVqoeYjCD/", Crypt.crypt("12345678901234567890", "$1$1234"));
    }

    @Test
    void testMd5CryptNullData() {
        assertThrows(NullPointerException.class, () -> Md5Crypt.md5Crypt((byte[]) null));
    }

    @Test
    void testMd5CryptStrings() {
        // Empty plaintext.
        assertEquals("$1$foo$9mS5ExwgIECGE5YKlD5o91", Crypt.crypt("", "$1$foo"));

        // The salt is truncated at the first "$" after the prefix, so trailing "$..." segments are ignored.
        final String expectedForSalt1234 = "$1$1234$ImZYBLmYC.rbBKg9ERxX70";
        assertEquals(expectedForSalt1234, Crypt.crypt("secret", "$1$1234"));
        assertEquals(expectedForSalt1234, Crypt.crypt("secret", "$1$1234$567"));
        assertEquals(expectedForSalt1234, Crypt.crypt("secret", "$1$1234$567$890"));

        // The salt is also truncated to a maximum of 8 characters.
        final String expectedForSalt12345678 = "$1$12345678$hj0uLpdidjPhbMMZeno8X/";
        assertEquals(expectedForSalt12345678, Crypt.crypt("secret", "$1$1234567890123456"));
        assertEquals(expectedForSalt12345678, Crypt.crypt("secret", "$1$123456789012345678"));
    }

    @Test
    void testMd5CryptWithEmptySalt() {
        // An empty salt does not match the required salt pattern.
        assertThrows(IllegalArgumentException.class, () -> Md5Crypt.md5Crypt("secret".getBytes(), ""));
    }

    @Test
    void testZeroOutInput() {
        final byte[] buffer = new byte[200];
        Arrays.fill(buffer, (byte) 'A');

        Md5Crypt.md5Crypt(buffer);

        // The input password buffer is zero-filled before md5Crypt returns, so nothing is left in memory.
        assertArrayEquals(new byte[buffer.length], buffer);
    }

}
