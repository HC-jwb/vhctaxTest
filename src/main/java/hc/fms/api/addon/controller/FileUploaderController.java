package hc.fms.api.addon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import hc.fms.api.addon.model.ResponseContainer;
import hc.fms.api.addon.model.ResponseStatus;
import hc.fms.api.addon.properties.FileUploadProperties;
import hc.fms.api.addon.vhctax.service.UploadStorageService;

@RestController
@RequestMapping("/addon/upload/*")
public class FileUploaderController {
	@Autowired
	private UploadStorageService uploadStorageService;
	@PostMapping("photo")
	public ResponseContainer<Void> uploadFile(@RequestParam("file") MultipartFile file) {
		ResponseContainer<Void> response = new ResponseContainer<>();
		try {
			response.setSuccess(true);
		} catch(Exception e) {
			response.setStatus(new ResponseStatus(e.getMessage()));
		}
		return response;
	}
}
