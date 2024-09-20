package com.makers.princemaker.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Snow
 */
@AllArgsConstructor
@Getter
public enum PrinceLevel {
    BABY_PRINCE("아기 왕자님"),
    JUNIOR_PRINCE("어린이 왕자님"),
    MIDDLE_PRINCE("청년 왕자님"),
    KING("왕"),
    DRAGON_SLAYER("용을 무찌른 왕");

    private final String description;
}
