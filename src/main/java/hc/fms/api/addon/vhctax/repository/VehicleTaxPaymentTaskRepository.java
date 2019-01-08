package hc.fms.api.addon.vhctax.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import hc.fms.api.addon.vhctax.entity.VehicleTaxTask;
@Repository
public interface VehicleTaxPaymentTaskRepository extends JpaRepository<VehicleTaxTask, Long>{
/*
 * @Query(value="select r from Reservation r join r.user u join  u.group ug join ug.company c where c.id=:compId and r.vehicleId=:vhcId and (r.startDate between :startDate and :endDate or r.endDate between :startDate and :endDate) and r.status IN :statList")
	List<Reservation> findAllByStartDateBetweenOrEndDateBetweenInCompanyAndVehicleIdAndStatCodeList(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("compId") Long compId, @Param("vhcId") Long vhcId, @Param("statList") List<String> statList);
 */
	@Query(value="select t from VehicleTaxTask t where t.taskType IN :typeList and t.dateValidTill between :fromDate and :toDate order by t.dateValidTill ASC")
	public List<VehicleTaxTask> listTaxTaskList(@Param("typeList") List<String> typeList, @Param("fromDate") String fromDate, @Param("toDate") String toDate);
	
	@Modifying
	@Query(value="update VehicleTaxTask t set t.paid=TRUE where t.id=:taskId")
	public void updatePaymentTaskPaid(@Param("taskId") Long taskId);
	
	@Query(value="select * from vhc_tax_task where paid != 1 and date_add(valid_till_dt,interval -remind DAY) <= now() order by valid_till_dt asc", nativeQuery=true)
	public List<VehicleTaxTask> findAllTaskForNotification();
}
