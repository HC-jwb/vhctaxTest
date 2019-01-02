package hc.fms.api.addon.vhctax.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hc.fms.api.addon.vhctax.entity.VehicleTaxPaymentTask;
@Repository
public interface VehicleTaxPaymentTaskRepository extends JpaRepository<VehicleTaxPaymentTask, Long>{

}
