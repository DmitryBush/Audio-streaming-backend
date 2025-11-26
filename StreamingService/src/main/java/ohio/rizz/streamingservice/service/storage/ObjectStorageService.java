package ohio.rizz.streamingservice.service.storage;

import io.minio.*;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.CompletableFuture;

@Service
public class ObjectStorageService {
    private final MinioClient minioClient;

    @Autowired
    public ObjectStorageService(@Value("${storage-service.endpoint}") String endpoint,
                                @Value("${storage-service.access-key}") String accessKey,
                                @Value("${storage-service.secret-key}") String secretKey) {
        minioClient = MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();
    }

    public void saveFile(File file, String bucket, String object) {
        try {
            boolean isBucketExist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
            if (!isBucketExist) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
            }

            minioClient.uploadObject(UploadObjectArgs.builder()
                                             .bucket(bucket)
                                             .object(object)
                                             .filename(file.getAbsolutePath())
                                             .build());
        } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException |
                 InvalidResponseException | IOException | NoSuchAlgorithmException | ServerException |
                 XmlParserException e) {
            throw new RuntimeException(e);
        }
    }

    @Async("fileUploadTaskExecutor")
    public CompletableFuture<Void> saveFileAsync(File file, String bucket, String object) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        try {
            saveFile(file, bucket, object);
            future.complete(null);
        } catch (RuntimeException e) {
            future.completeExceptionally(e);
        }
        return future;
    }

    public void deleteFile(String bucket, String object) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(bucket)
                    .object(object)
                    .build());
        } catch (ServerException | InsufficientDataException | ErrorResponseException | IOException |
                 NoSuchAlgorithmException | InvalidKeyException | InvalidResponseException | XmlParserException |
                 InternalException e) {
            throw new RuntimeException(e);
        }
    }

    public Resource loadResource(String bucket, String object) {
        try(InputStream inputStream = minioClient.getObject(GetObjectArgs.builder()
                                                                    .bucket(bucket).object(object).build())) {
            Resource resource = new ByteArrayResource(inputStream.readAllBytes());
            if (resource.exists() && resource.isReadable()) {
                return resource;
            }
            throw new IOException("The resource is missing or unavailable");
        } catch (ServerException | InsufficientDataException | ErrorResponseException | IOException |
                 NoSuchAlgorithmException | InvalidKeyException | InvalidResponseException | XmlParserException |
                 InternalException e) {
            throw new RuntimeException(e);
        }
    }

    public InputStreamResource loadStreamResource(String bucket, String object, Long startPosition, Long chunkSize) {
        try {
            InputStream inputStream = minioClient.getObject(GetObjectArgs.builder()
                                                                    .bucket(bucket).object(object).offset(startPosition)
                                                                    .length(chunkSize).build());
            return new InputStreamResource(inputStream);
        } catch (ServerException | InternalException | XmlParserException | InsufficientDataException |
                 ErrorResponseException | IOException | NoSuchAlgorithmException | InvalidKeyException |
                 InvalidResponseException e) {
            throw new RuntimeException(e);
        }
    }
}
