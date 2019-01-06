package hc.fms.api.addon.vhctax.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hc.fms.api.addon.vhctax.entity.TaxRegistrationPhoto;
@Repository
public interface TaxPhotoRepository extends JpaRepository<TaxRegistrationPhoto, Long>{

}
