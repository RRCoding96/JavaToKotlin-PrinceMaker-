package com.makers.princemaker.dto;

import com.makers.princemaker.type.PrinceLevel;
import com.makers.princemaker.type.SkillType;
import lombok.*;

/**
 * @author Snow
 */
public class EditPrince {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request {
        private PrinceLevel princeLevel;
        private SkillType skillType;
        private Integer experienceYears;
        private String name;
        private Integer age;
    }
}
