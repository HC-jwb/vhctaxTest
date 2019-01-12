package hc.fms.api.addon.vhctax.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hc.fms.api.addon.vhctax.entity.TaxPhoto;
import hc.fms.api.addon.vhctax.repository.TaxPhotoRepository;

@Service
public class UploadStorageService {
	@Autowired
	private TaxPhotoRepository taxPhotoRepository;
	public TaxPhoto savePhotoImage(TaxPhoto taxPhoto) {
		return taxPhotoRepository.save(taxPhoto);
	}
	
}
