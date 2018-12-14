package hc.fms.api.addon.report.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hc.fms.api.addon.report.entity.MileageStatDetail;

@Repository
public interface MileageStatDetailRepository extends JpaRepository<MileageStatDetail, Long>{

}
