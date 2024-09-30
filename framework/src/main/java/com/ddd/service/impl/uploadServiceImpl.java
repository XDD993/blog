package com.ddd.service.impl;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.ddd.domain.ResponseResult;
import com.ddd.enums.AppHttpCodeEnum;
import com.ddd.handler.exception.SystemException;
import com.ddd.service.uploadService;
import com.ddd.utils.PathUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class uploadServiceImpl implements uploadService {

	@Override
	public ResponseResult upload(MultipartFile img) throws IOException {
		String filename = img.getOriginalFilename();
		if (!filename.endsWith(".png")&&!filename.endsWith(".jpg")){
			throw new SystemException(AppHttpCodeEnum.FILE_TYPE_ERROR);
		}
		String filePath = PathUtils.generateFilePath(filename);

		String uploadFile = uploadFile(img,filePath);

		return ResponseResult.okResult(uploadFile);
	}

	private static String uploadFile(MultipartFile file, String filePath) throws IOException {
		// Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
		String endpoint = "";

		// 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
		String accessKeyId = "";
		String accessKeySecret = "";

		// 填写Bucket名称，例如examplebucket。
		String bucketName = "xddblog";
		// 填写Object完整路径，完整路径中不能包含Bucket名称，例如exampledir/exampleobject.txt。
		String objectName = filePath;
		// 填写本地文件的完整路径，例如D:\\localpath\\examplefile.txt。
		// 如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件流。
//		String filePath= "C:\\Users\\Administrator\\Desktop\\20240902134548.jpg";

		// 创建OSSClient实例。
		OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

		try {
			InputStream inputStream = file.getInputStream();
//			InputStream inputStream = new FileInputStream(filePath);
			// 创建PutObjectRequest对象。
			PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, inputStream);
			// 设置该属性可以返回response。如果不设置，则返回的response为空。
			putObjectRequest.setProcess("true");
			// 创建PutObject请求。
			PutObjectResult result = ossClient.putObject(putObjectRequest);
			Date expiration = new Date(System.currentTimeMillis() + 3600 * 1000);
			String url = ossClient.generatePresignedUrl(bucketName, objectName, expiration).toString();
			// 如果上传成功，则返回200。
			System.out.println(result.getResponse().getStatusCode());
			return url;
		} catch (OSSException oe) {
			System.out.println("Caught an OSSException, which means your request made it to OSS, "
					+ "but was rejected with an error response for some reason.");
			System.out.println("Error Message:" + oe.getErrorMessage());
			System.out.println("Error Code:" + oe.getErrorCode());
			System.out.println("Request ID:" + oe.getRequestId());
			System.out.println("Host ID:" + oe.getHostId());
		} catch (ClientException ce) {
			System.out.println("Caught an ClientException, which means the client encountered "
					+ "a serious internal problem while trying to communicate with OSS, "
					+ "such as not being able to access the network.");
			System.out.println("Error Message:" + ce.getMessage());
		} finally {
			if (ossClient != null) {
				ossClient.shutdown();
			}
		}
		return null;
	}
}


