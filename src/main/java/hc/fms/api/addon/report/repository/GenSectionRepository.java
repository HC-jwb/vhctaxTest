package hc.fms.api.addon.report.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hc.fms.api.addon.report.entity.GenSection;
@Repository
public interface GenSectionRepository extends JpaRepository<GenSection, GenSection.GenSectionId>{
	List<GenSection> findAllByReportId(Long reportId);
}
