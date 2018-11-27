package hc.fms.api.report.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hc.fms.api.report.entity.MileageStatDetail;

@Repository
public interface MileageStatDetailRepository extends JpaRepository<MileageStatDetail, Long>{

}
