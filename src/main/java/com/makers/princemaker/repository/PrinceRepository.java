package com.makers.princemaker.repository;

import com.makers.princemaker.code.StatusCode;
import com.makers.princemaker.entity.Prince;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Snow
 */
@Repository
public interface PrinceRepository extends JpaRepository<Prince, Long> {

    Optional<Prince> findByPrinceId(String princeId);

    List<Prince> findByStatusEquals(StatusCode status);
}
