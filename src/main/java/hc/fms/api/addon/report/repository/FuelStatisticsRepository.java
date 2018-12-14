package hc.fms.api.addon.report.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import hc.fms.api.addon.report.entity.FuelStatResult;
import hc.fms.api.addon.report.entity.FuelStatistics;

@Repository
public interface FuelStatisticsRepository extends JpaRepository<FuelStatistics, Long>{
	@Query(value="select f.stat_dt statDate, f.tracker_id trackerId, round(f.max,2) fuelEnd, round(f.min,2) fuelStart, round(m.max,2) mileageEnd, round(m.min,2) mileageStart, \r\n" + 
			"round((f.max - f.min),2) fuelUsed, round((m.max-m.min),2) distanceTravelled, round((m.max-m.min) /(f.max-f.min),2) fuelEffRate\r\n" + 
			"from fuel_stat f join fuel_stat m \r\n" + 
			"on f.report_id = m.report_id and f.tracker_id=m.tracker_id and f.stat_dt = m.stat_dt\r\n" + 
			"where f.type='F' and m.type='M' and f.min is not null and (f.max - f.min) !=0 and f.report_id=:reportId and f.tracker_id=:trackerId\r\n" + 
			"order by f.tracker_id, f.raw_dt", nativeQuery=true)
	public List<FuelStatResult> getFuelStatResultList(@Param("reportId") Long reportId, @Param("trackerId") Long trackerId);
}
