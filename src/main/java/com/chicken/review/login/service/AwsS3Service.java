package com.chicken.review.login.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.chicken.review.login.mapper.ReviewMapper;
import com.chicken.review.login.vo.ReviewVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class AwsS3Service {
    @Autowired
    private ReviewMapper reviewMapper;

    private static final String BUCKET_NAME = "awsreview-01";
    private static final String ACCESS_KEY = "AKIATN5LFDAUFYRPGYQJ";
    private static final String SECRET_KEY = "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";
    private AmazonS3 s3;

    public AwsS3Service() {
        AWSCredentials awsCredentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);

        s3 = AmazonS3Client.builder()
                .withRegion(Regions.AP_NORTHEAST_2)
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
    }

    public void s3FileUpload(MultipartFile file, ReviewVO review) throws IOException {
        if (!file.getOriginalFilename().equals("")) {
            File localFile = new File("C:\\lee\\files\\" + file.getOriginalFilename());
            file.transferTo(localFile);

            System.out.println(localFile.getName());
            PutObjectRequest req = new PutObjectRequest(BUCKET_NAME, localFile.getName(), localFile);
            req.setCannedAcl(CannedAccessControlList.PublicRead);
            String imageUrl = "https://awsreview-01.s3.ap-northeast-2.amazonaws.com/" + localFile.getName();
            review.setS3ImageUrl(imageUrl);
            s3.putObject(req);
        } else {
            review.setS3ImageUrl("");
        }
        reviewMapper.insertReview(review);
    }
}

