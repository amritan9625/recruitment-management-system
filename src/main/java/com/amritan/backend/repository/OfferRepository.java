package com.amritan.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.amritan.backend.entity.Offer;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long>{

}
