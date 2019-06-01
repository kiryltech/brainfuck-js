package tech.kiryl.vlq

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import org.assertj.core.api.Assertions.assertThat

object VlqEncoderSpek : Spek({

    describe("simple example") {
        assertThat(
            encode(
                listOf(0, 0, 0, 4),
                listOf(4, 0, 0, 0),
                listOf(19, 0, 0, 21),
                listOf(2, 0, 0, -20),
                listOf(8, 0, 0, 1),
                listOf(2, 0, 0, 2),
                listOf(8, 0, 0, 17),
                listOf(1, 0, 0, 1)
            )
        ).isEqualTo("AAAI,IAAA,mBAAqB,EAApB,QAAC,EAAE,QAAiB,CAAC")

    }

})