package hc.fms.api.addon.vhctax.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hc.fms.api.addon.vhctax.entity.TaxRegistrationPhoto;
import hc.fms.api.addon.vhctax.repository.TaxPhotoRepository;

@Service
public class UploadStorageService {
	@Autowired
	private TaxPhotoRepository taxPhotoRepository;
	public TaxRegistrationPhoto savePhotoImage(TaxRegistrationPhoto taxPhoto) {
		return taxPhotoRepository.save(taxPhoto);
	}
	
}
