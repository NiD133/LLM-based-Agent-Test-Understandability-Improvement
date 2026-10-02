package org.apache.commons.codec.digest;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.apache.commons.codec.digest.MurmurHash2;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true,
        resetStaticState = true, separateClassLoader = false)
public class MurmurHash2_ESTest extends MurmurHash2_ESTest_scaffolding {

    // -------------------------------------------------------------------------
    // hash32(byte[], int, int) — null-safety
    // -------------------------------------------------------------------------

    @Test(timeout = 4000)
    public void hash32_nullByteArray_throwsNullPointerException() throws Throwable {
        try {
            MurmurHash2.hash32((byte[]) null, -65, -65);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            verifyException("org.apache.commons.codec.digest.MurmurHash2", e);
        }
    }

    // -------------------------------------------------------------------------
    // hash32(String) — whole-string 32-bit hashes
    // -------------------------------------------------------------------------

    @Test(timeout = 4000)
    public void hash32_twoCharString_returnsExpectedHash() throws Throwable {
        int hash = MurmurHash2.hash32("Bl");
        assertEquals(-504122062, hash);
    }

    @Test(timeout = 4000)
    public void hash32_stringWithSpecialChars_returnsExpectedHash() throws Throwable {
        int hash = MurmurHash2.hash32("^{MC\"");
        assertEquals(-817914152, hash);
    }

    // -------------------------------------------------------------------------
    // hash32(String, int, int) — substring 32-bit hash
    // -------------------------------------------------------------------------

    @Test(timeout = 4000)
    public void hash32_emptySubstring_returnsExpectedHash() throws Throwable {
        int hash = MurmurHash2.hash32("", 0, 0);
        assertEquals(275646681, hash);
    }

    // -------------------------------------------------------------------------
    // hash64(String) — whole-string 64-bit hashes
    // -------------------------------------------------------------------------

    @Test(timeout = 4000)
    public void hash64_emptyString_returnsExpectedHash() throws Throwable {
        long hash = MurmurHash2.hash64("");
        assertEquals(-7207201254813729732L, hash);
    }

    @Test(timeout = 4000)
    public void hash64_singleCharacter_returnsExpectedHash() throws Throwable {
        long hash = MurmurHash2.hash64("v");
        assertEquals(5594253894753466330L, hash);
    }

    @Test(timeout = 4000)
    public void hash64_fourCharStringWithApostrophe_returnsExpectedHash() throws Throwable {
        long hash = MurmurHash2.hash64("'J-f");
        assertEquals(5768382861705900380L, hash);
    }

    @Test(timeout = 4000)
    public void hash64_sevenCharAlphanumericString_returnsExpectedHash() throws Throwable {
        long hash = MurmurHash2.hash64("BG7{/@,");
        assertEquals(8897355786490055066L, hash);
    }

    @Test(timeout = 4000)
    public void hash64_tenCharAlphanumericString_returnsExpectedHash() throws Throwable {
        long hash = MurmurHash2.hash64("M78]F%@JR*");
        assertEquals(8542654808481837325L, hash);
    }

    @Test(timeout = 4000)
    public void hash64_stringWithSpaceAndDigits_returnsExpectedHash() throws Throwable {
        long hash = MurmurHash2.hash64("G,5 2lXZ1083");
        assertEquals(-199748896782609694L, hash);
    }

    @Test(timeout = 4000)
    public void hash64_longStringWithSpecialChars_returnsExpectedHash() throws Throwable {
        long hash = MurmurHash2.hash64("*abc_PldN~tL$r");
        assertEquals(1862178600039130994L, hash);
    }

    @Test(timeout = 4000)
    public void hash64_longStringWithQuoteAndSymbols_returnsExpectedHash() throws Throwable {
        long hash = MurmurHash2.hash64("y\"1B>9T<!hw2,E^U'vm");
        assertEquals(-1450341502340637772L, hash);
    }

    // -------------------------------------------------------------------------
    // hash64(String, int, int) — substring 64-bit hash / out-of-bounds
    // -------------------------------------------------------------------------

    @Test(timeout = 4000)
    public void hash64_substringOffsetExceedsStringLength_throwsStringIndexOutOfBoundsException() throws Throwable {
        try {
            MurmurHash2.hash64("sM", 367, 367);
            fail("Expecting exception: StringIndexOutOfBoundsException");
        } catch (StringIndexOutOfBoundsException e) {
            // expected: offset 367 is far beyond the 2-character input string
        }
    }
}
