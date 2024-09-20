package com.makers.princemaker.repository;

import com.makers.princemaker.entity.WoundedPrince;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Snow
 */
@Repository
public interface WoundedPrinceRepository extends JpaRepository<WoundedPrince, Long> {

}
