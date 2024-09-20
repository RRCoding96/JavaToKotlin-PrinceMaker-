package com.makers.princemaker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.makers.princemaker.code.PrinceMakerErrorCode;
import com.makers.princemaker.dto.CreatePrince;
import com.makers.princemaker.dto.PrinceDto;
import com.makers.princemaker.exception.PrinceMakerException;
import com.makers.princemaker.service.PrinceMakerService;
import com.makers.princemaker.type.PrinceLevel;
import com.makers.princemaker.type.SkillType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Snow
 */
@WebMvcTest(PrinceMakerController.class)
@MockBean(JpaMetamodelMappingContext.class)
class PrinceMakerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PrinceMakerService princeMakerService;

    private ObjectMapper objectMapper = new ObjectMapper();

    protected MediaType contentType =
            new MediaType(MediaType.APPLICATION_JSON.getType(),
                    MediaType.APPLICATION_JSON.getSubtype(),
                    StandardCharsets.UTF_8);

    @Test
    void getAllPrince() throws Exception {
        //given
        PrinceDto warriorKing = PrinceDto.builder()
                .skillType(SkillType.WARRIOR)
                .princeLevel(PrinceLevel.KING)
                .princeId("princeId")
                .build();
        PrinceDto intellectualJuniorPrince = PrinceDto.builder()
                .skillType(SkillType.INTELLECTUAL)
                .princeLevel(PrinceLevel.JUNIOR_PRINCE)
                .princeId("princeId2")
                .build();
        given(princeMakerService.getAllPrince())
                .willReturn(Arrays.asList(warriorKing, intellectualJuniorPrince));

        //when
        //then
        mockMvc.perform(get("/princes").contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("$.[0].skillType",
                                is(SkillType.WARRIOR.name())))
                .andExpect(
                        jsonPath("$.[0].princeLevel",
                                is(PrinceLevel.KING.name())))
                .andExpect(
                        jsonPath("$.[1].skillType",
                                is(SkillType.INTELLECTUAL.name())))
                .andExpect(
                        jsonPath("$.[1].princeLevel",
                                is(PrinceLevel.JUNIOR_PRINCE.name())));
    }

    @Test
    void createPrinceSuccess() throws Exception {
        //given
        CreatePrince.Request createPrinceRequest = new CreatePrince.Request(
                PrinceLevel.BABY_PRINCE,
                SkillType.INTELLECTUAL,
                20,
                "princeId",
                "name",
                20
        );
        given(princeMakerService.createPrince(any()))
                .willReturn(
                        new CreatePrince.Response(
                                PrinceLevel.BABY_PRINCE,
                                SkillType.INTELLECTUAL,
                                20,
                                "princeId",
                                "name",
                                20
                        )
                );

        //when
        //then
        mockMvc.perform(post("/create-prince")
                        .contentType(contentType)
                        .content(objectMapper.writeValueAsString(createPrinceRequest))
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(
                        jsonPath("$.skillType",
                                is(SkillType.INTELLECTUAL.name())))
                .andExpect(
                        jsonPath("$.princeLevel",
                                is(PrinceLevel.BABY_PRINCE.name())));
    }

    @Test
    void createPrinceFailed() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(post("/create-prince")
                        .contentType(contentType)
                        .content(
                                "{\"princeLevel\":\"BABY_PRINCE\",\"skillType\":\"INTELLECTUAL\",\"princeId\":\"princeId\",\"name\":\"name\",\"age\":20}"
                        )
                )
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andExpect(
                        jsonPath("$.errorCode",
                                is(PrinceMakerErrorCode.INVALID_REQUEST.name())))
                .andExpect(
                        jsonPath("$.errorMessage",
                                is(PrinceMakerErrorCode.INVALID_REQUEST.getMessage())));
    }

    @Test
    void testErrorMessage() throws Exception {
        //given
        given(princeMakerService.getAllPrince())
                .willThrow(new PrinceMakerException(PrinceMakerErrorCode.NO_SUCH_PRINCE));

        //when
        //then
        mockMvc.perform(get("/princes").contentType(contentType))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("$.errorCode",
                                is(PrinceMakerErrorCode.NO_SUCH_PRINCE.name())));

    }
}

