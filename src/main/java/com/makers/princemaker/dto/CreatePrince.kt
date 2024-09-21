package com.makers.princemaker.dto;

import com.makers.princemaker.entity.Prince;
import com.makers.princemaker.type.PrinceLevel;
import com.makers.princemaker.type.SkillType;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Snow
 */
public class CreatePrince {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request {
        @NotNull
        private PrinceLevel princeLevel;

        @NotNull
        private SkillType skillType;

        @NotNull
        @Min(0)
        private Integer experienceYears;

        @NotNull
        @Size(min = 3, max = 50, message = "invalid princeId")
        private String princeId;

        @NotNull
        @Size(min = 2, max = 50, message = "invalid name")
        private String name;

        @NotNull
        @Min(18)
        private Integer age;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Response {
        private PrinceLevel princeLevel;
        private SkillType skillType;
        private Integer experienceYears;
        private String princeId;
        private String name;
        private Integer age;

        public static Response fromEntity(Prince prince) {
            return Response.builder()
                    .princeLevel(prince.getPrinceLevel())
                    .skillType(prince.getSkillType())
                    .experienceYears(prince.getExperienceYears())
                    .princeId(prince.getPrinceId())
                    .name(prince.getName())
                    .age(prince.getAge())
                    .build();
        }
    }
}
