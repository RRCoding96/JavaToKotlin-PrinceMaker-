package com.makers.princemaker.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Snow
 */
@AllArgsConstructor
@Getter
public enum SkillType {
    WARRIOR("전투형"),
    INTELLECTUAL("지능형"),
    MAGE("마술사형");

    private final String description;
}
