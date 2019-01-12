package hc.fms.api.addon.vhctax.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hc.fms.api.addon.vhctax.entity.TaxPhoto;
@Repository
public interface TaxPhotoRepository extends JpaRepository<TaxPhoto, Long>{

}
