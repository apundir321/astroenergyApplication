package com.astroenergy.java.astroenergyApplication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.astroenergy.java.astroenergyApplication.model.Common;
import com.astroenergy.java.astroenergyApplication.service.AWSS3Service;
import com.astroenergy.java.astroenergyApplication.service.CommonService;


@RestController
@RequestMapping("/common")
public class CommonController {

	@Autowired 
	private AWSS3Service awsService;
	@Autowired
	private CommonService commonService;
	@PostMapping("/uploadPic")
     public ResponseEntity<?> uploadPic(@RequestPart(value="file" ,required =true) final MultipartFile multipartFile)throws Exception{
		try {
			String imageUrl=awsService.uploadCommonFile(multipartFile);
			Common commonImage=new Common();
			commonImage.setImage(imageUrl);
			Common uploadedImage=commonService.addImage(commonImage);
			return new ResponseEntity<>(uploadedImage,HttpStatus.OK);
		}
		catch(Exception e) {
			throw e;
		}
	}
	@GetMapping("/all")
	public ResponseEntity<?> getAllCommon(){
		try {
			List<Common>allImage=commonService.getAllCommonImage();
			return new ResponseEntity<>(allImage,HttpStatus.OK);
		}
		catch(Exception e) {
			throw e;
		}
	}
	@DeleteMapping("/delete/{commonId}")
	public ResponseEntity<?> getAllCommon(@PathVariable Long commonId) throws Exception{
		try {
		Common deletedImage=commonService.deleteCommonImage(commonId);
			return new ResponseEntity<>(deletedImage,HttpStatus.OK);
		}
		catch(Exception e) {
			throw e;
		}
	}
}
