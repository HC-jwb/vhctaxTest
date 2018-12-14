package hc.fms.api.addon.report.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import hc.fms.api.addon.report.entity.ReportGen;

@Repository
public interface ReportGenRepository extends JpaRepository <ReportGen, Long>{

	//public List<ReportGen> findAllByOrderByCreatedDateDesc();

	//public List<ReportGen> findAllByFuelReportProcessed(boolean processed);
	@Query(value="select gen from ReportGen gen where gen.clientId=:clientId and gen.fuelReportId is not null and gen.mileageReportId is not null order by gen.createdDate desc")
	public List<ReportGen> findAllFuelEffReportByClientIdOrderByCreatedDateDesc(@Param("clientId") String clientId);
	@Query(value="select gen from ReportGen gen where gen.clientId=:clientId and gen.fillDrainReportId is not null order by gen.createdDate desc")
	public List<ReportGen> findAllFillDrainReportByClientIdOrderByCreatedDateDesc(@Param("clientId") String clientId);
	
	@Query(value="select gen from ReportGen gen where gen.clientId=:clientId and  gen.fuelReportId is not null and gen.mileageReportId is not null and gen.fuelReportProcessed=false")
	public List<ReportGen> findAllInProgressFuelEffRateGenByClientId(@Param("clientId") String clientId);
	@Query(value="select gen from ReportGen gen where gen.clientId=:clientId and  gen.fillDrainReportId is not null and gen.fuelReportProcessed=false")
	public List<ReportGen> findAllInProgressFillDrainGenByClientId(@Param("clientId") String clientId);
}
