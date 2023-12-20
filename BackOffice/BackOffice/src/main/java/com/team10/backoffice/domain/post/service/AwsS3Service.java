package com.team10.backoffice.domain.post.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.team10.backoffice.etc.utils.CommonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
@RequiredArgsConstructor
@Service
public class AwsS3Service {

	private final AmazonS3 amazonS3Client;

	@Value( "${cloud.aws.s3.bucket}" )
	private String bucketName;

	public String uploadFileV1( String category, MultipartFile multipartFile ) {
		validateFileExists( multipartFile );

		String fileName = CommonUtils.buildFileName( category, multipartFile.getOriginalFilename() );

		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentType( multipartFile.getContentType() );

		try( InputStream inputStream = multipartFile.getInputStream() ) {
			amazonS3Client.putObject( new PutObjectRequest( bucketName, fileName, inputStream, objectMetadata )
					.withCannedAcl( CannedAccessControlList.PublicRead ) );
		}
		catch( IOException e ) {
			throw new RuntimeException( e.getMessage() );
//			throw new FileUploadFailedException();
		}

		return amazonS3Client.getUrl( bucketName, fileName ).toString();
	}

	private void validateFileExists( MultipartFile multipartFile ) {
		if( multipartFile.isEmpty() ) {
			throw new RuntimeException( "multipartFile is empty" );
//			throw new EmptyFileException();
		}
	}

}
