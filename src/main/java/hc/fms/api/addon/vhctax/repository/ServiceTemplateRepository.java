package hc.fms.api.addon.vhctax.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hc.fms.api.addon.vhctax.entity.ServiceTemplate;
@Repository
public interface ServiceTemplateRepository extends JpaRepository<ServiceTemplate, Long>{
	public List<ServiceTemplate> findAllByOrderById();
}
