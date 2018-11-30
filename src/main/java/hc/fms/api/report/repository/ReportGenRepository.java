package hc.fms.api.report.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hc.fms.api.report.entity.ReportGen;

@Repository
public interface ReportGenRepository extends JpaRepository <ReportGen, Long>{

	public List<ReportGen> findAllByOrderByCreatedDateDesc();

	public List<ReportGen> findAllByFuelReportProcessed(boolean processed);

}
