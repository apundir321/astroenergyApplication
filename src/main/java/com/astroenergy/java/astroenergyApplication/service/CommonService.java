package com.astroenergy.java.astroenergyApplication.service;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.astroenergy.java.astroenergyApplication.dao.CommonRepository;
import com.astroenergy.java.astroenergyApplication.model.Common;

@Service
public class CommonService {
	
@Autowired 
private CommonRepository commonRepo;

private final static Logger log=LogManager.getLogger(CommonService.class);

public Common addImage(Common image) {
	try {log.info("saving image");
		return commonRepo.save(image);
	}
	catch(Exception e) {
		log.error("Error occured while saving image"+e.getMessage());
		throw e;
	}
}

public List<Common> getAllCommonImage(){
	try {log.info("Getting all common images");
		return commonRepo.findByDeletedAtIsNullOrderByIdDesc();
	}
	catch(Exception e) {
		log.error("Error occured while getting all common image"+e.getMessage());
		throw e;
	}
}
public Common deleteCommonImage(Long commonImageId) throws Exception {
	try {
	Common image=	commonRepo.findByIdAndDeletedAtIsNull(commonImageId);
	if(image==null) {
		log.error("Common image does not exist with id: "+commonImageId);
		throw new Exception("Common image does not exist with id: "+commonImageId);
	}
	image.setDeletedAt(new Date());
	return commonRepo.save(image);
	
	}
	catch(Exception e) {
		throw e;
	}
}
}
