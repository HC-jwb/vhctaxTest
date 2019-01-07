package hc.fms.api.addon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import hc.fms.api.addon.model.ResponseContainer;
import hc.fms.api.addon.model.ResponseStatus;
import hc.fms.api.addon.vhctax.entity.TaxRegistrationPhoto;
import hc.fms.api.addon.vhctax.service.UploadStorageService;

@RestController
@RequestMapping("/addon/upload/*")
public class FileUploaderController {
	@Autowired
	private UploadStorageService uploadStorageService;
	@PostMapping("photo")
	public ResponseContainer<TaxRegistrationPhoto> uploadFile(@RequestParam("file") MultipartFile file) {
		ResponseContainer<TaxRegistrationPhoto> response = new ResponseContainer<>();
		try {
			TaxRegistrationPhoto taxPhoto = new TaxRegistrationPhoto();
			String fileName = file.getOriginalFilename();
			byte [] data = file.getBytes();
			taxPhoto.setImage(data);
			int lastIdx = fileName.lastIndexOf(".");
			taxPhoto.setName(fileName.substring(0, lastIdx));
			taxPhoto.setType(fileName.substring(lastIdx +1).toLowerCase());
			taxPhoto = uploadStorageService.savePhotoImage(taxPhoto);
			response.setPayload(taxPhoto);
			response.setSuccess(true);
		} catch(Exception e) {
			response.setStatus(new ResponseStatus(e.getMessage()));
		}
		return response;
	}
}
