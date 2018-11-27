package hc.fms.api.report.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hc.fms.api.report.entity.FuelStatistics;

@Repository
public interface FuelStatisticsRepository extends JpaRepository<FuelStatistics, Long>{

}
