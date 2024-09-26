package com.makers.princemaker.service

import com.makers.princemaker.code.PrinceMakerErrorCode
import com.makers.princemaker.code.StatusCode
import com.makers.princemaker.constant.PrinceMakerConstant
import com.makers.princemaker.controller.CreatePrince
import com.makers.princemaker.controller.toCreatePrinceResponse
import com.makers.princemaker.dto.EditPrince
import com.makers.princemaker.dto.PrinceDetailDto
import com.makers.princemaker.dto.PrinceDto
import com.makers.princemaker.entity.Prince
import com.makers.princemaker.entity.WoundedPrince
import com.makers.princemaker.exception.PrinceMakerException
import com.makers.princemaker.repository.PrinceRepository
import com.makers.princemaker.repository.WoundedPrinceRepository
import com.makers.princemaker.type.PrinceLevel
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.stream.Collectors

/**
 * @author Snow
 */
@Service
class PrinceMakerService(
    private val princeRepository: PrinceRepository,
    private val woundedPrinceRepository: WoundedPrinceRepository
) {
    @Transactional
    fun createPrince(request: CreatePrince.Request): CreatePrince.Response {
        validateCreatePrinceRequest(request)
        val prince = Prince(
            id = null,
            princeLevel = request.princeLevel!!,
            skillType = request.skillType!!,
            status = StatusCode.HEALTHY,
            experienceYears = request.experienceYears!!,
            princeId = request.princeId!!,
            name = request.name!!,
            age = request.age!!,
            createdAt = null,
            updatedAt = null
        )
        princeRepository.save(prince)
        return prince.toCreatePrinceResponse()
    }

    private fun validateCreatePrinceRequest(request: CreatePrince.Request) {
        princeRepository.findByPrinceId(request.princeId!!) ?.let {
            throw PrinceMakerException(PrinceMakerErrorCode.DUPLICATED_PRINCE_ID)
        }

        if (request.princeLevel == PrinceLevel.KING
            && request.experienceYears!! < PrinceMakerConstant.MIN_KING_EXPERIENCE_YEARS
        ) {
            throw PrinceMakerException(PrinceMakerErrorCode.LEVEL_AND_EXPERIENCE_YEARS_NOT_MATCH)
        }
        if (request.princeLevel == PrinceLevel.MIDDLE_PRINCE
            && (request.experienceYears!! > PrinceMakerConstant.MIN_KING_EXPERIENCE_YEARS
                    || request.experienceYears < PrinceMakerConstant.MAX_JUNIOR_EXPERIENCE_YEARS)
        ) {
            throw PrinceMakerException(PrinceMakerErrorCode.LEVEL_AND_EXPERIENCE_YEARS_NOT_MATCH)
        }
        if (request.princeLevel == PrinceLevel.JUNIOR_PRINCE
            && request.experienceYears!! > PrinceMakerConstant.MAX_JUNIOR_EXPERIENCE_YEARS
        ) {
            throw PrinceMakerException(PrinceMakerErrorCode.LEVEL_AND_EXPERIENCE_YEARS_NOT_MATCH)
        }
    }

    @get:Transactional
    val allPrince: List<PrinceDto>
        get() = princeRepository.findByStatusEquals(StatusCode.HEALTHY)
            .map { PrinceDto.fromEntity(it) }

    @Transactional
    fun getPrince(princeId: String): PrinceDetailDto {
        return princeRepository.findByPrinceId(princeId)
            ?.let { prince: Prince -> PrinceDetailDto.fromEntity(prince) }
            ?: throw PrinceMakerException(PrinceMakerErrorCode.NO_SUCH_PRINCE)
    }

    @Transactional
    fun editPrince(
        princeId: String, request: EditPrince.Request
    ): PrinceDetailDto {
        val prince = princeRepository.findByPrinceId(princeId)
            ?: throw PrinceMakerException(PrinceMakerErrorCode.NO_SUCH_PRINCE)
        prince.princeLevel = request.princeLevel
        prince.skillType = request.skillType
        prince.experienceYears = request.experienceYears
        prince.name = request.name
        prince.age = request.age
        return PrinceDetailDto.fromEntity(prince)
    }

    @Transactional
    fun woundPrince(
        princeId: String
    ): PrinceDetailDto {
        val prince = princeRepository.findByPrinceId(princeId)
            ?: throw PrinceMakerException(PrinceMakerErrorCode.NO_SUCH_PRINCE)
        prince.status = StatusCode.WOUNDED
        val woundedPrince = WoundedPrince.builder()
            .princeId(prince.princeId)
            .name(prince.name)
            .build()
        woundedPrinceRepository.save(woundedPrince)
        return PrinceDetailDto.fromEntity(prince)
    }
}
