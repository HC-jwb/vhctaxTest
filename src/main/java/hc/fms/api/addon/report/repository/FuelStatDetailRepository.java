package hc.fms.api.addon.report.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hc.fms.api.addon.report.entity.FuelStatDetail;

@Repository
public interface FuelStatDetailRepository extends JpaRepository<FuelStatDetail, Long>{

}
