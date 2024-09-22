package com.makers.princemaker.controller

import com.makers.princemaker.entity.Prince
import com.makers.princemaker.service.PrinceMakerService
import com.makers.princemaker.type.PrinceLevel
import com.makers.princemaker.type.SkillType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@RestController
class CreatePrinceController (
    private val princeMakerService: PrinceMakerService
) {
    @PostMapping("/create-prince")
    fun createPrince(
        @Valid @RequestBody request: CreatePrince.Request
    ): CreatePrince.Response = princeMakerService.createPrince(request)
}

class CreatePrince {

    data class Request(
        @field:NotNull
        val princeLevel:  PrinceLevel? = null,

        @field:NotNull
        val skillType:  SkillType? = null,

        @field:NotNull
        @field:Min(0)
        val experienceYears:  Int? = null,

        @field:NotNull
        @field:Size(
            min = 3,
            max = 50,
            message = "invalid princeId"
        )
        val princeId:  String? = null,

        @field:NotNull
        @field:Size(
            min = 2,
            max = 50,
            message = "invalid name"
        )
        val name:  String? = null,

        @field:NotNull
        @field:Min(18)
        val age:  Int? = null
    )

    class Response(
        val princeLevel: PrinceLevel? = null,
        val skillType: SkillType? = null,
        val experienceYears: Int? = null,
        val princeId: String? = null,
        val name: String? = null,
        val age: Int? = null
    )
}

fun Prince.toCreatePrinceResponse() = CreatePrince.Response(
    princeLevel = this.princeLevel,
    skillType = this.skillType,
    experienceYears = this.experienceYears,
    princeId = this.princeId,
    name = this.name,
    age = this.age
)