package com.makers.princemaker.dto;

import com.makers.princemaker.code.StatusCode;
import com.makers.princemaker.entity.Prince;
import com.makers.princemaker.type.PrinceLevel;
import com.makers.princemaker.type.SkillType;
import com.makers.princemaker.util.DateTimeUtils;
import lombok.*;

/**
 * @author Snow
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PrinceDetailDto {
    private PrinceLevel princeLevel;
    private SkillType skillType;
    private Integer experienceYears;
    private String princeId;
    private String name;
    private Integer age;
    private StatusCode status;
    private String birthDate;

    public static PrinceDetailDto fromEntity(Prince prince) {
        return PrinceDetailDto.builder()
                .princeLevel(prince.getPrinceLevel())
                .skillType(prince.getSkillType())
                .experienceYears(prince.getExperienceYears())
                .princeId(prince.getPrinceId())
                .name(prince.getName())
                .age(prince.getAge())
                .status(prince.getStatus())
                .birthDate(DateTimeUtils.getLocalDateTimeString(prince.getCreatedAt()))
                .build();
    }
}
