package com.makers.princemaker.service;

import com.makers.princemaker.code.StatusCode;
import com.makers.princemaker.dto.CreatePrince;
import com.makers.princemaker.dto.PrinceDetailDto;
import com.makers.princemaker.dto.PrinceDto;
import com.makers.princemaker.dto.EditPrince;
import com.makers.princemaker.entity.Prince;
import com.makers.princemaker.entity.WoundedPrince;
import com.makers.princemaker.exception.PrinceMakerException;
import com.makers.princemaker.repository.PrinceRepository;
import com.makers.princemaker.repository.WoundedPrinceRepository;
import com.makers.princemaker.type.PrinceLevel;
import com.makers.princemaker.code.PrinceMakerErrorCode;
import com.makers.princemaker.constant.PrinceMakerConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.makers.princemaker.code.StatusCode.HEALTHY;

/**
 * @author Snow
 */
@Service
@RequiredArgsConstructor
public class PrinceMakerService {
    private final PrinceRepository princeRepository;
    private final WoundedPrinceRepository woundedPrinceRepository;

    @Transactional
    public CreatePrince.Response createPrince(CreatePrince.Request request) {
        validateCreatePrinceRequest(request);

        Prince prince = Prince.builder()
                .princeLevel(request.getPrinceLevel())
                .skillType(request.getSkillType())
                .status(HEALTHY)
                .experienceYears(request.getExperienceYears())
                .princeId(request.getPrinceId())
                .name(request.getName())
                .age(request.getAge())
                .build();
        princeRepository.save(prince);
        return CreatePrince.Response.fromEntity(prince);
    }

    private void validateCreatePrinceRequest(CreatePrince.Request request) {
        princeRepository.findByPrinceId(request.getPrinceId())
                .ifPresent((prince) -> {
                    throw new PrinceMakerException(PrinceMakerErrorCode.DUPLICATED_PRINCE_ID);
                });

        if (request.getPrinceLevel() == PrinceLevel.KING
                && request.getExperienceYears() < PrinceMakerConstant.MIN_KING_EXPERIENCE_YEARS) {
            throw new PrinceMakerException(PrinceMakerErrorCode.LEVEL_AND_EXPERIENCE_YEARS_NOT_MATCH);
        }

        if (request.getPrinceLevel() == PrinceLevel.MIDDLE_PRINCE
                && (request.getExperienceYears() > PrinceMakerConstant.MIN_KING_EXPERIENCE_YEARS
                || request.getExperienceYears() < PrinceMakerConstant.MAX_JUNIOR_EXPERIENCE_YEARS)
        ) {
            throw new PrinceMakerException(PrinceMakerErrorCode.LEVEL_AND_EXPERIENCE_YEARS_NOT_MATCH);
        }

        if (request.getPrinceLevel() == PrinceLevel.JUNIOR_PRINCE
                && request.getExperienceYears() > PrinceMakerConstant.MAX_JUNIOR_EXPERIENCE_YEARS) {
            throw new PrinceMakerException(PrinceMakerErrorCode.LEVEL_AND_EXPERIENCE_YEARS_NOT_MATCH);
        }
    }

    @Transactional
    public List<PrinceDto> getAllPrince() {
        return princeRepository.findByStatusEquals(HEALTHY)
                .stream().map(PrinceDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public PrinceDetailDto getPrince(String princeId) {
        return princeRepository.findByPrinceId(princeId)
                .map(PrinceDetailDto::fromEntity)
                .orElseThrow(
                        () -> new PrinceMakerException(PrinceMakerErrorCode.NO_SUCH_PRINCE)
                );
    }

    @Transactional
    public PrinceDetailDto editPrince(
            String princeId, EditPrince.Request request
    ) {
        Prince prince = princeRepository.findByPrinceId(princeId)
                .orElseThrow(
                        () -> new PrinceMakerException(PrinceMakerErrorCode.NO_SUCH_PRINCE)
                );
        prince.setPrinceLevel(request.getPrinceLevel());
        prince.setSkillType(request.getSkillType());
        prince.setExperienceYears(request.getExperienceYears());
        prince.setName(request.getName());
        prince.setAge(request.getAge());

        return PrinceDetailDto.fromEntity(prince);
    }

    @Transactional
    public PrinceDetailDto woundPrince(
            String princeId
    ) {
        Prince prince = princeRepository.findByPrinceId(princeId)
                .orElseThrow(
                        () -> new PrinceMakerException(PrinceMakerErrorCode.NO_SUCH_PRINCE)
                );

        prince.setStatus(StatusCode.WOUNDED);

        WoundedPrince woundedPrince = WoundedPrince.builder()
                .princeId(prince.getPrinceId())
                .name(prince.getName())
                .build();
        woundedPrinceRepository.save(woundedPrince);
        return PrinceDetailDto.fromEntity(prince);
    }
}
