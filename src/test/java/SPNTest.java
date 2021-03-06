import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SPNTest {

    SPN spn;
    int[] bitpermutationValues;
    int[] sBOXValues;
    int r;
    int n;
    int m;
    int s;
    int fullKey;

    @BeforeEach
    void beforeEachTest() {
        bitpermutationValues = new int[]{0, 4, 8, 12, 1, 5, 9, 13, 2, 6, 10, 14, 3, 7, 11, 15};
        sBOXValues = new int[]{14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7};
        fullKey = 0b0011_1010_1001_0100_1101_0110_0011_1111;

        r = 4;      //Rundenschlüssel
        n = 4;      //Anzahl Bit eines Blocks
        m = 4;      //Anzahl Blöcke
        s = 32;     //Länge des Schlüssel in Bit

        spn = new SPN(r, n, m, s, fullKey, bitpermutationValues, sBOXValues);
    }

    @AfterEach
    void afterEachTest() {
        spn = null;
    }

    @Test
    void keysTest() {
        //given
        final int[] fullKeyStringRegular = {
                0b0011101010010100
                , 0b1010100101001101
                , 0b1001010011010110
                , 0b0100110101100011
                , 0b1101011000111111};

        //then
        assertArrayEquals(spn.getRoundKeys(), fullKeyStringRegular);
    }

    @Test
    void keyReverseTest() {
        //given
        final int[] fullKeyStringReverse = {
                0b1101011000111111
                , 0b0100111000110101
                , 0b1010011100011010
                , 0b1101001110000101
                , 0b0011101010010100};

        //then
        assertArrayEquals(spn.getRoundKeysInverse(), fullKeyStringReverse);

    }


    @Test
    void intToArrayTest() {
        //given
        int x = 0b1010_1111_0011_1100;

        //when
        int[] arrX = spn.intToArray(x);

        //then
        assertEquals(0b1010, arrX[0]);
        assertEquals(0b1111, arrX[1]);
        assertEquals(0b0011, arrX[2]);
        assertEquals(0b1100, arrX[3]);
    }

    @Test
    void intArrayToIntTest() {
        //given
        int[] intArray = {0b1010
                , 0b1111
                , 0b0011
                , 0b1100};

        int expect = 0b1010_1111_0011_1100;

        //when
        int result = spn.arrayToInt(intArray);

        //then
        assertEquals(expect, result);
    }

    @Test
    void bitpermutationTest() {
        //given
        int actual = 0b0011_1010_1001_0100;
        int expected = 0b0110_0001_1100_1010;

        //when
        int bitsAferBP = spn.bitPermutation(actual);

        //then
        assertEquals(expected, bitsAferBP);
    }

    @Test
    void bitpermutationTest2() {
        //given
        int actual = 0b1110_0001_0110_1000;
        int expected = 0b1001_1010_1010_0100;

        //when
        int bitsAferBP = spn.bitPermutation(actual);

        //then
        assertEquals(expected, bitsAferBP);
    }

    @Test
    void runSPNTest() {
        //given
        final int testKey = 0b0001_0001_0010_1000_1000_1100_0000_0000;
        final int testX = 0b0001_0010_1000_1111;

        SPN spnTestKey = new SPN(r, n, m, s, testKey, bitpermutationValues, sBOXValues);

        //when
        int result = spnTestKey.startSPN(testX, false);
        int expected = 0b1010_1110_1011_0100;

        //then
        assertEquals(expected, result);
    }

}