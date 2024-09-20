package com.makers.princemaker.dto;

import com.makers.princemaker.entity.Prince;
import com.makers.princemaker.type.PrinceLevel;
import com.makers.princemaker.type.SkillType;
import lombok.*;

/**
 * @author Snow
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PrinceDto {
    private PrinceLevel princeLevel;
    private SkillType skillType;
    private String princeId;

    public static PrinceDto fromEntity(Prince prince) {
        return PrinceDto.builder()
                .princeLevel(prince.getPrinceLevel())
                .skillType(prince.getSkillType())
                .princeId(prince.getPrinceId())
                .build();
    }
}
