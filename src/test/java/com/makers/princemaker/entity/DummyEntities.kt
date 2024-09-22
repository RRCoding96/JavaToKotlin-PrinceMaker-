package com.makers.princemaker.entity

import com.makers.princemaker.code.StatusCode
import com.makers.princemaker.type.PrinceLevel
import com.makers.princemaker.type.SkillType
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime
import javax.persistence.EnumType
import javax.persistence.Enumerated

fun dummyPrince(
    id: Long? = 1L,
    princeLevel: PrinceLevel = PrinceLevel.BABY_PRINCE,
    skillType: SkillType = SkillType.WARRIOR,
    status: StatusCode = StatusCode.HEALTHY,
    experienceYears: Int = 1980,
    princeId: String = "princeId",
    name: String = "name",
    age: Int = 30,
    createdAt: LocalDateTime? = LocalDateTime.now(),
    updatedAt: LocalDateTime? = LocalDateTime.now()
) = Prince(
    id = id,
    princeLevel = princeLevel,
    skillType = skillType,
    status = status,
    experienceYears = experienceYears,
    princeId = princeId,
    name = name,
    age = age,
    createdAt = createdAt,
    updatedAt = updatedAt
)