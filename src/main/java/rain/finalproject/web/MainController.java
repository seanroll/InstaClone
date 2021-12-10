package rain.finalproject.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {
	@GetMapping("/UploadTest")
	public String uploadTest() {
		return "Main/UploadTest";
	}

	@PostMapping("/UploadTest")
	public String uploadTest(@RequestParam(name = "fileData") String fileData, Model model) {
		// Verification of image data to-do

		// For now, just send data back (database stuff will be tested later)
		model.addAttribute("uploadedData", fileData);

		return "Main/UploadTest";
	}




}
