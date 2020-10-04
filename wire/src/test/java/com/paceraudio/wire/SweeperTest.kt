package com.paceraudio.wire

import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.lang.reflect.Method
import java.lang.reflect.InvocationTargetException

class SweeperTest {

    private lateinit var sweeper: Sweeper
    private lateinit var isInPositiveRange: Method
    private lateinit var calcValueAtStep: Method

    //    RED   : [1020, 1275, 255, 510]
    //    GREEN : [0, 255, 765, 1020]
    //    BLUE  : [510, 765, 1275, 0]

    //0, 255, 510, 765, 1020, 1275
    private val step1 = 0
    private val step2 = 255
    private val step3 = 510
    private val step4 = 765
    private val step5 = 1020
    private val step6 = 1275


    @Before
    fun setUp() {
        sweeper = Sweeper()
        isInPositiveRange = sweeper.javaClass.getDeclaredMethod(
            "isInPositiveRange",
            Int::class.java,
            Int::class.java,
            Int::class.java,
            Int::class.java
        )
        isInPositiveRange.isAccessible = true

        calcValueAtStep = sweeper.javaClass.getDeclaredMethod(
            "calcValueAtStep",
            IntArray::class.java, Int
            ::class.java
        )
        calcValueAtStep.isAccessible = true
    }


    @Test
    fun `test valid ranges`() {
        val value = 5
        val begin = 3
        val end = 7
        val steps = 10
        val result = isInPositiveRange.invoke(sweeper, value, begin, end, steps)
        if (result is Boolean) {
            assert(result)
        }
    }

    @Test
    fun `test valid ranges with break`() {
        val value = 5
        val begin = 8
        val end = 6
        val steps = 10
        val result = isInPositiveRange.invoke(sweeper, value, begin, end, steps)
        if (result is Boolean) {
            assert(result)
        }
    }

    @Test
    fun `test value equal to end`() {
        val value = 5
        val begin = 1
        val end = 5
        val steps = 10
        val result = isInPositiveRange.invoke(sweeper, value, begin, end, steps)
        if (result is Boolean) {
            assert(!result)
        }
    }

    @Test
    fun `test value greater than end`() {
        val value = 5
        val begin = 8
        val end = 5
        val steps = 10
        val result = isInPositiveRange.invoke(sweeper, value, begin, end, steps)
        if (result is Boolean) {
            assert(!result)
        }
    }

    @Test
    fun `test value greater than steps`() {
        val value = 11
        val begin = 8
        val end = 5
        val steps = 10
        val result = isInPositiveRange.invoke(sweeper, value, begin, end, steps)
        if (result is Boolean) {
            assert(!result)
        }
    }

    @Test
    fun `test negative value out of range`() {
        val value = -1
        val begin = 0
        val end = 5
        val steps = 10
        val result = isInPositiveRange.invoke(sweeper, value, begin, end, steps)
        if (result is Boolean) {
            assert(!result)
        }
    }

    @Test(expected = BadRangeException::class)
    fun `test bad range negative begin param`() {
        val value = 1
        val begin = -2
        val end = 5
        val steps = 10
        try {
            isInPositiveRange.invoke(sweeper, value, begin, end, steps)
        } catch (e: InvocationTargetException) {
            e.cause?.let { throwable ->
                throw throwable
            }
        }
    }

    @Test(expected = BadRangeException::class)
    fun `test bad range negative end param`() {
        val value = 1
        val begin = 2
        val end = -5
        val steps = 10
        try {
            isInPositiveRange.invoke(sweeper, value, begin, end, steps)
        } catch (e: InvocationTargetException) {
            e.cause?.let { throwable ->
                throw throwable
            }
        }
    }

    @Test(expected = BadRangeException::class)
    fun `test bad range negative max param`() {
        val value = 1
        val begin = 2
        val end = 5
        val steps = -10
        try {
            isInPositiveRange.invoke(sweeper, value, begin, end, steps)
        } catch (e: InvocationTargetException) {
            e.cause?.let { throwable ->
                throw throwable
            }
        }
    }

    @Test(expected = BadRangeException::class)
    fun `test bad range end of range greater than max`() {
        val value = 5
        val begin = 2
        val end = 12
        val steps = 10
        try {
            isInPositiveRange.invoke(sweeper, value, begin, end, steps)
        } catch (e: InvocationTargetException) {
            e.cause?.let { throwable ->
                throw throwable
            }
        }
    }

//    RED   : [1020, 1275, 255, 510]
//    GREEN : [0, 255, 765, 1020]
//    BLUE  : [510, 765, 1275, 0]

    @Test
    fun `test red value at step 0`() {
        val value = calcValueAtStep.invoke(sweeper, R_STEPS, step1)
        assertEquals("value $value should be 255!", 255, value)
    }

    @Test
    fun `test red value at step 1`() {
        val value = calcValueAtStep.invoke(sweeper, R_STEPS, step1 +1)
        assertEquals("value $value should be 255!", 255, value)
    }

    @Test
    fun `test red value at step 254`() {
        val value = calcValueAtStep.invoke(sweeper, R_STEPS, step2 -1)
        assertEquals("value $value should be 255!", 255, value)
    }

    @Test
    fun `test red value at step 255`() {
        val value = calcValueAtStep.invoke(sweeper, R_STEPS, step2)
        assertEquals("value $value should be 255!", 255, value)
    }

    @Test
    fun `test red value at step 256`() {
        val value = calcValueAtStep.invoke(sweeper, R_STEPS, step2 + 1)
        assertEquals("value $value should be 254!", 254, value)
    }

    @Test
    fun `test red value at step 266`() {
        val value = calcValueAtStep.invoke(sweeper, R_STEPS, step2 + 10)
        assertEquals("value $value should be 245!", 245, value)
    }

    @Test
    fun `test red value at step 509`() {
        val value = calcValueAtStep.invoke(sweeper, R_STEPS, step3 -1)
        assertEquals("value $value should be 1!", 1, value)
    }
    @Test
    fun `test red value at step 510`() {
        val value = calcValueAtStep.invoke(sweeper, R_STEPS, step3)
        assertEquals("value $value should be 0!", 0, value)
    }

    @Test
    fun `test red value at step 511`() {
        val value = calcValueAtStep.invoke(sweeper, R_STEPS, step3 +1)
        assertEquals("value $value should be 0!", 0, value)
    }

    @Test
    fun `test red value at step 1019`() {
        val value = calcValueAtStep.invoke(sweeper, R_STEPS, step5 -1)
        assertEquals("value $value should be 0!", 0, value)
    }
    @Test
    fun `test red value at step 1020`() {
        val value = calcValueAtStep.invoke(sweeper, R_STEPS, step5)
        assertEquals("value $value should be 0!", 0, value)
    }

    @Test
    fun `test red value at step 1021`() {
        val value = calcValueAtStep.invoke(sweeper, R_STEPS, step5 +1)
        assertEquals("value $value should be 1!", 1, value)
    }

    @Test
    fun `test red value at step 1070`() {
        val value = calcValueAtStep.invoke(sweeper, R_STEPS, step5 +50)
        assertEquals("value $value should be 50!", 50, value)
    }

    @Test
    fun `test red value at step 1274`() {
        val value = calcValueAtStep.invoke(sweeper, R_STEPS, step6 -1)
        assertEquals("value $value should be 254!", 254, value)
    }

    @Test
    fun `test red value at step 1275`() {
        val value = calcValueAtStep.invoke(sweeper, R_STEPS, step6)
        assertEquals("value $value should be 255!", 255, value)
    }

    @Test
    fun `test red value at step 1276`() {
        val value = calcValueAtStep.invoke(sweeper, R_STEPS, step6 +1)
        assertEquals("value $value should be 255!", 255, value)
    }

    @Test
    fun `test red value at step 1529`() {
        val value = calcValueAtStep.invoke(sweeper, R_STEPS, step6 +254)
        assertEquals("value $value should be 255!", 255, value)
    }

//    RED   : [1020, 1275, 255, 510]
//    GREEN : [0, 255, 765, 1020]
//    BLUE  : [510, 765, 1275, 0]

    @Test
    fun `test green value at step 0`() {
        val value = calcValueAtStep.invoke(sweeper, G_STEPS, step1)
        assertEquals("value $value should be 0!", 0, value)
    }

    @Test
    fun `test green value at step 1`() {
        val value = calcValueAtStep.invoke(sweeper, G_STEPS, 1)
        assertEquals("value $value should be 1!", 1, value)
    }

    //0, 255, 510, 765, 1020, 1275
    @Test
    fun `test green value at step 254`() {
        val value = calcValueAtStep.invoke(sweeper, G_STEPS, 254)
        assertEquals("value $value should be 254!", 254, value)
    }

    @Test
    fun `test green value at step 255`() {
        val value = calcValueAtStep.invoke(sweeper, G_STEPS, step2)
        assertEquals("value $value should be 255!", 255, value)
    }

    @Test
    fun `test green value at step 256`() {
        val value = calcValueAtStep.invoke(sweeper, G_STEPS, step2 + 1)
        assertEquals("value $value should be 255!", 255, value)
    }

    @Test
    fun `test green value at step 509`() {
        val value = calcValueAtStep.invoke(sweeper, G_STEPS, step3 -1)
        assertEquals("value $value should be 255!", 255, value)
    }

    @Test
    fun `test green value at step 510`() {
        val value = calcValueAtStep.invoke(sweeper, G_STEPS, step3)
        assertEquals("value $value should be 255!", 255, value)
    }

    @Test
    fun `test green value at step 511`() {
        val value = calcValueAtStep.invoke(sweeper, G_STEPS, step3 + 1)
        assertEquals("value $value should be 255!", 255, value)
    }

    @Test
    fun `test green value at step 764`() {
        val value = calcValueAtStep.invoke(sweeper, G_STEPS, step4 -1)
        assertEquals("value $value should be 255!", 255, value)
    }

    @Test
    fun `test green value at step 765`() {
        val value = calcValueAtStep.invoke(sweeper, G_STEPS, step4)
        assertEquals("value $value should be 255!", 255, value)
    }

    @Test
    fun `test green value at step 766`() {
        val value = calcValueAtStep.invoke(sweeper, G_STEPS, step4 +1)
        assertEquals("value $value should be 254!", 254, value)
    }

    @Test
    fun `test green value at step 1019`() {
        val value = calcValueAtStep.invoke(sweeper, G_STEPS, step5 -1)
        assertEquals("value $value should be 1!", 1, value)
    }

    @Test
    fun `test green value at step 1020`() {
        val value = calcValueAtStep.invoke(sweeper, G_STEPS, step5)
        assertEquals("value $value should be 0!", 0, value)
    }

    @Test
    fun `test green value at step 1021`() {
        val value = calcValueAtStep.invoke(sweeper, G_STEPS, step5 +1)
        assertEquals("value $value should be 0!", 0, value)
    }

    @Test
    fun `test green value at step 1274`() {
        val value = calcValueAtStep.invoke(sweeper, G_STEPS, step6 -1)
        assertEquals("value $value should be 0!", 0, value)
    }

    @Test
    fun `test green value at step 1275`() {
        val value = calcValueAtStep.invoke(sweeper, G_STEPS, step6)
        assertEquals("value $value should be 0!", 0, value)
    }

    @Test
    fun `test green value at step 1276`() {
        val value = calcValueAtStep.invoke(sweeper, G_STEPS, step6 +1)
        assertEquals("value $value should be 0!", 0, value)
    }

    @Test
    fun `test green value at step 1529`() {
        val value = calcValueAtStep.invoke(sweeper, G_STEPS, step6 +254)
        assertEquals("value $value should be 0!", 0, value)
    }

    //    BLUE  : [510, 765, 1275, 0]

    @Test
    fun `test blue value at step 0`() {
        val value = calcValueAtStep.invoke(sweeper, B_STEPS, step1)
        assertEquals("value $value should be 0!", 0, value)
    }

    @Test
    fun `test blue value at step 1`() {
        val value = calcValueAtStep.invoke(sweeper, B_STEPS, step1 +1)
        assertEquals("value $value should be 0!", 0, value)
    }

    //0, 255, 510, 765, 1020, 1275
    //    BLUE  : [510, 765, 1275, 0]
    @Test
    fun `test blue value at step 254`() {
        val value = calcValueAtStep.invoke(sweeper, B_STEPS, step2 -1)
        assertEquals("value $value should be 0!", 0, value)
    }

    @Test
    fun `test blue value at step 255`() {
        val value = calcValueAtStep.invoke(sweeper, B_STEPS, step2)
        assertEquals("value $value should be 0!", 0, value)
    }

    @Test
    fun `test blue value at step 256`() {
        val value = calcValueAtStep.invoke(sweeper, B_STEPS, step2 + 1)
        assertEquals("value $value should be 0!", 0, value)
    }

    //    BLUE  : [510, 765, 1275, 0]
    @Test
    fun `test blue value at step 509`() {
        val value = calcValueAtStep.invoke(sweeper, B_STEPS, step3 -1)
        assertEquals("value $value should be 0!", 0, value)
    }

    @Test
    fun `test blue value at step 510`() {
        val value = calcValueAtStep.invoke(sweeper, B_STEPS, step3)
        assertEquals("value $value should be 0!", 0, value)
    }

    @Test
    fun `test blue value at step 511`() {
        val value = calcValueAtStep.invoke(sweeper, B_STEPS, step3 + 1)
        assertEquals("value $value should be 1!", 1, value)
    }

    //    BLUE  : [510, 765, 1275, 0]
    @Test
    fun `test blue value at step 764`() {
        val value = calcValueAtStep.invoke(sweeper, B_STEPS, step4 -1)
        assertEquals("value $value should be 254!", 254, value)
    }

    @Test
    fun `test blue value at step 765`() {
        val value = calcValueAtStep.invoke(sweeper, B_STEPS, step4)
        assertEquals("value $value should be 255!", 255, value)
    }

    @Test
    fun `test blue value at step 766`() {
        val value = calcValueAtStep.invoke(sweeper, B_STEPS, step4 +1)
        assertEquals("value $value should be 255!", 255, value)
    }

    @Test
    fun `test blue value at step 1019`() {
        val value = calcValueAtStep.invoke(sweeper, B_STEPS, step5 -1)
        assertEquals("value $value should be 255!", 255, value)
    }

    @Test
    fun `test blue value at step 1020`() {
        val value = calcValueAtStep.invoke(sweeper, B_STEPS, step5)
        assertEquals("value $value should be 255!", 255, value)
    }

    @Test
    fun `test blue value at step 1021`() {
        val value = calcValueAtStep.invoke(sweeper, B_STEPS, step5 +1)
        assertEquals("value $value should be 255!", 255, value)
    }

    @Test
    fun `test blue value at step 1274`() {
        val value = calcValueAtStep.invoke(sweeper, B_STEPS, step6 -1)
        assertEquals("value $value should be 255!", 255, value)
    }

    @Test
    fun `test blue value at step 1275`() {
        val value = calcValueAtStep.invoke(sweeper, B_STEPS, step6)
        assertEquals("value $value should be 255!", 255, value)
    }

    @Test
    fun `test blue value at step 1276`() {
        val value = calcValueAtStep.invoke(sweeper, B_STEPS, step6 +1)
        assertEquals("value $value should be 254!", 254, value)
    }

    @Test
    fun `test blue value at step 1529`() {
        val value = calcValueAtStep.invoke(sweeper, B_STEPS, step6 +254)
        assertEquals("value $value should be 1!", 1, value)
    }
}