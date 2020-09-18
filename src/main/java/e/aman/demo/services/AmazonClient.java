package e.aman.demo.services;



import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;


@Service
public class AmazonClient {

	String s;
	 @Autowired
	    private AmazonS3 amazonS3;
	    @Value("${aws.s3.bucket}")
	    private String bucketName;
	 
	    // @Async annotation ensures that the method is executed in a different background thread 
	    // but not consume the main thread.
	    @Async
	    public String uploadFile(final MultipartFile multipartFile) {
	        try {
	            final File file = convertMultiPartFileToFile(multipartFile);
	            s = uploadFileToS3Bucket(bucketName, file);
	            file.delete(); 
	           // To remove the file locally created in the project folder.
	        
	        
	        } catch (final AmazonServiceException ex) {
	        }
			return s;
	    }
	 
	    private File convertMultiPartFileToFile(final MultipartFile multipartFile) {
	        final File file = new File(multipartFile.getOriginalFilename());
	        try (final FileOutputStream outputStream = new FileOutputStream(file)) {
	            outputStream.write(multipartFile.getBytes());
	        } catch (final IOException ex) {
	        }
	        return file;
	    }
	 
	    private String uploadFileToS3Bucket(final String bucketName, final File file) {
	        final String uniqueFileName = LocalDateTime.now() + "_" + file.getName();
	        final PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, uniqueFileName, file);
	        amazonS3.putObject(putObjectRequest);
	        
	        return uniqueFileName;
	    }	    
	
}
