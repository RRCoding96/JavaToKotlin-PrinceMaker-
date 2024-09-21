package com.makers.princemaker.service

import com.makers.princemaker.code.PrinceMakerErrorCode
import com.makers.princemaker.code.StatusCode
import com.makers.princemaker.constant.PrinceMakerConstant
import com.makers.princemaker.dto.CreatePrince
import com.makers.princemaker.entity.Prince
import com.makers.princemaker.entity.PrinceMock
import com.makers.princemaker.exception.PrinceMakerException
import com.makers.princemaker.repository.PrinceRepository
import com.makers.princemaker.repository.WoundedPrinceRepository
import com.makers.princemaker.type.PrinceLevel
import com.makers.princemaker.type.SkillType
import io.kotest.assertions.assertSoftly
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import java.util.*

class PrinceMakerServiceKotest : BehaviorSpec({

    // 직접 모킹 해야됨
    val princeRepository: PrinceRepository = mockk()
    val woundedPrinceRepository: WoundedPrinceRepository = mockk()

    val princeMakerService = PrinceMakerService(
        princeRepository = princeRepository,
        woundedPrinceRepository = woundedPrinceRepository
    )

    Given("프린스 생성을 진행할 때") {

        val request = CreatePrince.Request(
            PrinceLevel.MIDDLE_PRINCE,
            SkillType.INTELLECTUAL,
            7,
            "princeId",
            "name",
            28
        )
        val juniorPrince = PrinceMock.createPrince(
            PrinceLevel.JUNIOR_PRINCE,
            SkillType.INTELLECTUAL,
            PrinceMakerConstant.MAX_JUNIOR_EXPERIENCE_YEARS,
            "princeId"
        )

        every {
            princeRepository.save(any())
        } returns juniorPrince

        When("princeId가 중복되지 않고 정상 요청이 오면") {
            every {
                princeRepository.findByPrinceId(any())
            } returns Optional.empty()

            val result = princeMakerService.createPrince(request)
            Then("정상 응답") {
                assertSoftly(result) {
                    princeLevel shouldBe PrinceLevel.MIDDLE_PRINCE
                    skillType shouldBe SkillType.INTELLECTUAL
                    experienceYears shouldBe 7
                }

            }
        }

        When("princeId가 중복되고 정상 요청이 오면") {
            every {
                princeRepository.findByPrinceId(any())
            } returns Optional.of(juniorPrince)

            val ex = shouldThrow<PrinceMakerException> {
                princeMakerService.createPrince(request)
            }
            Then("중복 오류 응답") {
                ex.princeMakerErrorCode shouldBe PrinceMakerErrorCode.DUPLICATED_PRINCE_ID
            }
        }
    }

})
