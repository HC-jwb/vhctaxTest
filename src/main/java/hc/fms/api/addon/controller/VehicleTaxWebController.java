package hc.fms.api.addon.controller;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import hc.fms.api.addon.vhctax.entity.TaxRegistrationPhoto;
import hc.fms.api.addon.vhctax.repository.TaxPhotoRepository;

@Controller
@RequestMapping("/addon")
public class VehicleTaxWebController {
	@Autowired
	private TaxPhotoRepository taxPhotoRepository;
	@GetMapping("/vhctax/photoimg/{photoId}")
	public void sendBinaryAsImageContent(@PathVariable("photoId") Long photoId, HttpServletResponse response) throws IOException {
		TaxRegistrationPhoto registrationPhoto = taxPhotoRepository.findById(photoId).get();
		response.setContentType(String.format("image/%s", registrationPhoto.getType()));
		OutputStream os = response.getOutputStream();
		os.write(registrationPhoto.getImage());
	}
}
