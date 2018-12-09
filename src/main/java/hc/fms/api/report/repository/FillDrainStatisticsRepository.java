package hc.fms.api.report.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hc.fms.api.report.entity.FillDrainStatistics;

@Repository
public interface FillDrainStatisticsRepository extends JpaRepository<FillDrainStatistics, Long>{

}
