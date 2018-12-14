package hc.fms.api.addon.report.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hc.fms.api.addon.report.entity.FillDrainStatistics;

@Repository
public interface FillDrainStatisticsRepository extends JpaRepository<FillDrainStatistics, Long>{
	public List<FillDrainStatistics> findAllByReportIdAndTrackerIdOrderByEventIdAsc(Long reportId, Long trackerId);
}
