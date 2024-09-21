package com.makers.princemaker.service

import com.makers.princemaker.code.PrinceMakerErrorCode
import com.makers.princemaker.constant.PrinceMakerConstant
import com.makers.princemaker.dto.CreatePrince
import com.makers.princemaker.entity.Prince
import com.makers.princemaker.entity.PrinceMock
import com.makers.princemaker.exception.PrinceMakerException
import com.makers.princemaker.repository.PrinceRepository
import com.makers.princemaker.repository.WoundedPrinceRepository
import com.makers.princemaker.type.PrinceLevel
import com.makers.princemaker.type.SkillType
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*

/**
 * @author Snow
 */
@ExtendWith(MockitoExtension::class)
internal class PrinceMakerServiceTest {
    @Mock
    private val princeRepository: PrinceRepository? = null

    @Mock
    private val woundedPrinceRepository: WoundedPrinceRepository? = null

    @InjectMocks
    private val princeMakerService: PrinceMakerService? = null

    @Test
    fun princeTest() {
            //given
            val juniorPrince = PrinceMock.createPrince(
                PrinceLevel.JUNIOR_PRINCE, SkillType.INTELLECTUAL,
                PrinceMakerConstant.MAX_JUNIOR_EXPERIENCE_YEARS, "princeId"
            )
            BDDMockito.given(princeRepository!!.findByPrinceId(ArgumentMatchers.anyString()))
                .willReturn(Optional.of(juniorPrince))

            //when
            val prince = princeMakerService!!.getPrince("princeId")

            //then
            Assertions.assertEquals(
                PrinceLevel.JUNIOR_PRINCE,
                prince.princeLevel
            )
            Assertions.assertEquals(SkillType.INTELLECTUAL, prince.skillType)
            Assertions.assertEquals(
                PrinceMakerConstant.MAX_JUNIOR_EXPERIENCE_YEARS,
                prince.experienceYears
            )
        }

    @Test
    fun createPrinceTest_success() {
        //given
        val request = CreatePrince.Request(
            PrinceLevel.MIDDLE_PRINCE, SkillType.INTELLECTUAL,
            7,
            "princeId",
            "name",
            28
        )
        val captor = ArgumentCaptor.forClass(
            Prince::class.java
        )

        //when
        val response = princeMakerService!!.createPrince(request)

        //then
        Mockito.verify(
            princeRepository, Mockito.times(1)
        )?.save(captor.capture())
        val savedPrince = captor.value
        Assertions.assertEquals(
            PrinceLevel.MIDDLE_PRINCE,
            savedPrince.princeLevel
        )
        Assertions.assertEquals(SkillType.INTELLECTUAL, savedPrince.skillType)
        Assertions.assertEquals(7, savedPrince.experienceYears)
        Assertions.assertEquals(PrinceLevel.MIDDLE_PRINCE, response.princeLevel)
        Assertions.assertEquals(SkillType.INTELLECTUAL, response.skillType)
        Assertions.assertEquals(7, response.experienceYears)
    }

    @Test
    fun createPrinceTest_failed_with_duplicated() {
        //given
        val juniorPrince = PrinceMock.createPrince(
            PrinceLevel.JUNIOR_PRINCE,
            SkillType.INTELLECTUAL,
            PrinceMakerConstant.MAX_JUNIOR_EXPERIENCE_YEARS,
            "princeId"
        )
        val request = CreatePrince.Request(
            PrinceLevel.JUNIOR_PRINCE, SkillType.INTELLECTUAL,
            3,
            "princeId",
            "name",
            28
        )
        BDDMockito.given(princeRepository!!.findByPrinceId(ArgumentMatchers.anyString()))
            .willReturn(Optional.of(juniorPrince))

        //when
        val exception = Assertions.assertThrows(
            PrinceMakerException::class.java
        ) { princeMakerService!!.createPrince(request) }
        //then
        Assertions.assertEquals(
            PrinceMakerErrorCode.DUPLICATED_PRINCE_ID,
            exception.princeMakerErrorCode
        )
    }

    @Test
    fun createPrinceTest_failed_with_invalid_experience() {
        //given
        val request = CreatePrince.Request(
            PrinceLevel.KING, SkillType.INTELLECTUAL,
            PrinceMakerConstant.MIN_KING_EXPERIENCE_YEARS - 3,
            "princeId",
            "name",
            28
        )
        BDDMockito.given(princeRepository!!.findByPrinceId(ArgumentMatchers.anyString()))
            .willReturn(Optional.empty())

        //when
        val exception = Assertions.assertThrows(
            PrinceMakerException::class.java
        ) { princeMakerService!!.createPrince(request) }
        //then
        Assertions.assertEquals(
            PrinceMakerErrorCode.LEVEL_AND_EXPERIENCE_YEARS_NOT_MATCH,
            exception.princeMakerErrorCode
        )
    }
}
