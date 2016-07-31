package com.mukhbir.controller;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
@RequestMapping("/")
public class HelloWorldController {

	@RequestMapping(method = RequestMethod.GET)
	public String sayHello(ModelMap model) {
		model.addAttribute("greeting", "Hello World");
		return "welcome";
	}

	@RequestMapping(value = "/helloagain", method = RequestMethod.GET)
	public String sayHelloAgain(ModelMap model) {
		model.addAttribute("greeting", "Hello World Again, from Spring 4 MVC");
		System.out.println("i was here");
		return "welcome";
	}
	@RequestMapping(value = "/files", method = RequestMethod.POST)
	public void getFile(HttpServletResponse response,@RequestParam("file") MultipartFile file) {
		try {
			System.out.println(file.getName());
			// get your file as InputStream
			File i1 = new File("/Users/roopghosh/Desktop/VISA.jpg");
			InputStream is = new FileInputStream( i1 );
			// copy it to response's OutputStream
			IOUtils.copy(is, response.getOutputStream());
			response.flushBuffer();
		} catch (IOException ex) {
			throw new RuntimeException("IOError writing file to output stream");
		}

	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public void getImage(ModelMap model,@RequestParam("file") MultipartFile file) {
		MultipartFile ml = (MultipartFile) file;
		System.out.println(ml.getName());
	}
	@RequestMapping(value = { "/testupload" }, method = RequestMethod.POST, produces =
			"application/json")
	public @ResponseBody
	ResponseEntity<?> testUpload(
			@RequestParam(value = "filedata", required = false) MultipartFile filedata,
			final HttpServletRequest request) throws IOException {

		InputStream is = null;
		if (filedata == null) {
			is = request.getInputStream();
		}
		else {
			is = filedata.getInputStream();
		}
		byte[] bytes = IOUtils.toByteArray(is);
		System.out.println("read " + bytes.length + " bytes.");

		return new ResponseEntity<String>(null, null, HttpStatus.OK);
	}
}