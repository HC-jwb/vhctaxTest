package hc.fms.api.report.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import hc.fms.api.report.entity.ReportGen;

@Repository
public interface ReportGenRepository extends JpaRepository <ReportGen, Long>{

	//public List<ReportGen> findAllByOrderByCreatedDateDesc();

	//public List<ReportGen> findAllByFuelReportProcessed(boolean processed);
	@Query(value="select gen from ReportGen gen where gen.clientId=:clientId and gen.fuelReportId is not null and gen.mileageReportId is not null order by gen.createdDate desc")
	public List<ReportGen> findAllFuelEffReportByClientIdOrderByCreatedDateDesc(@Param("clientId") String clientId);
	@Query(value="select gen from ReportGen gen where gen.clientId=:clientId and gen.fillDrainReportId is not null order by gen.createdDate desc")
	public List<ReportGen> findAllFillDrainReportByClientIdOrderByCreatedDateDesc(@Param("clientId") String clientId);
	
	@Query(value="select gen from ReportGen gen where gen.clientId=:clientId and  gen.fuelReportId is not null and gen.mileageReportId is not null and gen.fuelReportProcessed=false")
	public List<ReportGen> findAllProcessingFuelEffRateGenByClientId(@Param("clientId") String clientId);
	@Query(value="select gen from ReportGen gen where gen.clientId=:clientId and  gen.fillDrainReportId is not null and gen.fuelReportProcessed=false")
	public List<ReportGen> findAllProcessingFillDrainGenByClientId(@Param("clientId") String clientId);
}
