package com.makers.princemaker.dto

import com.makers.princemaker.controller.CreatePrince
import com.makers.princemaker.type.PrinceLevel
import com.makers.princemaker.type.SkillType

fun dummyCreatePriceRequest() : CreatePrince.Request =
    CreatePrince.Request(
        princeLevel = PrinceLevel.JUNIOR_PRINCE,
        skillType = SkillType.WARRIOR,
        experienceYears = 10,
        princeId = "princeId",
        name = "name",
        age = 35
    )