package e.aman.demo.controller;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import e.aman.demo.services.AmazonClient;


@RestController
@RequestMapping("/storage")
public class BucketController {
	
	@Autowired
	    private AmazonClient service;
	
	@Value("${endpointUrl}")
    private String endpointUrl;
	
	
	@Value("${aws.s3.bucket}")
    private String bucketName;
	
	
	 
	    @PostMapping(value= "/upload")
	    public String uploadFile(@RequestPart(value= "file") final MultipartFile multipartFile) {
	        String fileName = service.uploadFile(multipartFile);
	        
	        String fileUrl = "";
	            fileUrl = endpointUrl + "/" + bucketName + "/" + fileName;
	            return fileUrl;
	  	      	    }	
	
}
