package hc.fms.api.report.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import hc.fms.api.report.entity.FuelStatResult;
import hc.fms.api.report.entity.FuelStatistics;

@Repository
public interface FuelStatisticsRepository extends JpaRepository<FuelStatistics, Long>{
	@Query(value="select f.stat_dt statDate, f.tracker_id trackerId, f.max fuelEnd, f.min fuelStart,m.max mileageEnd, m.min mileageStart, (f.max - f.min) fuelUsed, (m.max-m.min) distanceTravelled, (m.max-m.min) /(f.max-f.min) fuelEffRate\r\n" + 
			"from fuel_stat f join fuel_stat m \r\n" + 
			"on f.report_id = m.report_id and f.tracker_id=m.tracker_id and f.stat_dt = m.stat_dt\r\n" + 
			"where f.type='F' and m.type='M' and f.report_id=:reportId\r\n" + 
			"order by f.tracker_id, f.raw_dt", nativeQuery=true)
	public List<FuelStatResult> getFuelStatResultList(@Param("reportId") Long reportId);
}
