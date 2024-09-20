package com.makers.princemaker.entity;

import com.makers.princemaker.code.StatusCode;
import com.makers.princemaker.type.PrinceLevel;
import com.makers.princemaker.type.SkillType;

import java.time.LocalDateTime;

/**
 * @author Snow
 */
public class PrinceMock {
    public static Prince createPrince(
            PrinceLevel princeLevel,
            SkillType skillType,
            Integer experienceYears,
            String princeId
    ) {
        return Prince.builder()
                .princeLevel(princeLevel)
                .skillType(skillType)
                .experienceYears(experienceYears)
                .princeId(princeId)
                .name("name")
                .age(28)
                .status(StatusCode.HEALTHY)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
