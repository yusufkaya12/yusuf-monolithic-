package com.threepounds.caseproject.util;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;

public class S3Util {

  private static AWSCredentials credentials =  new BasicAWSCredentials(
            "AKIAYWFWVLA7D33YGTCJ",
                "YpelcBmdMXbMRQj51f/BffKnlYOgAB865Ogab1vJ"
  );

  private static AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
      .withRegion(Regions.EU_WEST_1)
      .withCredentials(new AWSStaticCredentialsProvider(credentials))
      .build();

  public static void uploadObject(String bucketName,String keyName, InputStream inputStream) {

    try {

      TransferManager tm = TransferManagerBuilder.standard()
              .withS3Client(s3Client)
              .build();
      // TransferManager processes all transfers asynchronously,
      // so this call returns immediately.
      Upload upload = tm.upload(bucketName, keyName, inputStream, null);
      System.out.println("Object upload started");

      // Optionally, wait for the upload to finish before continuing.
      upload.waitForCompletion();
      System.out.println("Object upload complete");
    } catch (AmazonServiceException e) {
      // The call was transmitted successfully, but Amazon S3 couldn't process
      // it, so it returned an error response.
      e.printStackTrace();
    } catch (SdkClientException e) {
      // Amazon S3 couldn't be contacted for a response, or the client
      // couldn't parse the response from Amazon S3.
      e.printStackTrace();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }

  }
  public static String getPreSignedUrl(String bucketName,String keyName) {

      Calendar calendar = Calendar.getInstance();
      calendar.setTime(new Date());
      calendar.add(Calendar.DATE, 1); // Generated URL will be valid for 24 hours
     return s3Client.generatePresignedUrl(bucketName, keyName, calendar.getTime(), HttpMethod.GET).toString();

  }
}
