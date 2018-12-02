package hc.fms.api.report.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hc.fms.api.report.entity.GenSection;
@Repository
public interface GenSectionRepository extends JpaRepository<GenSection, GenSection.GenSectionId>{
	List<GenSection> findAllByReportId(Long reportId);
}
