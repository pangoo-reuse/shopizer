package com.salesmanager.core.business.repositories.promotion;

import com.salesmanager.core.model.promotion.Advertise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AdvertiseRepository extends JpaRepository<Advertise, Integer> {

    @Query("select distinct a from Advertise as a left join fetch a.banners ab left  join  fetch  ab.descriptions")
    List<Advertise> findFullList();

    @Query("select distinct a from Advertise as a left join fetch a.banners ab left  join  fetch  ab.descriptions where  a.id = ?1")
    Advertise findFullOne(Integer id);

    @Query("select distinct a from Advertise as a  where  a.id = ?1")
    Advertise findOne(Integer id);

    @Query("select distinct a from Advertise as a left join fetch a.banners ab left  join  fetch  ab.descriptions where  a.code = ?1")
    Advertise findOneFullByCode(String code);

    boolean existsByCode(String code);
}
