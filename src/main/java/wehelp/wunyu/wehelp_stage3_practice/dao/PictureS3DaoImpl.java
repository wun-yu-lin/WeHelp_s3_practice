package wehelp.wunyu.wehelp_stage3_practice.dao;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import wehelp.wunyu.wehelp_stage3_practice.exception.FileDeleteException;
import wehelp.wunyu.wehelp_stage3_practice.exception.FileUploadException;
import wehelp.wunyu.wehelp_stage3_practice.model.S3pictureModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.UUID;


@Component
public class PictureS3DaoImpl implements PictureS3Dao {
    @Value("${aws.s3.bucket.name}")
    private String bucketName;


    private final AmazonS3 s3Client;

    public PictureS3DaoImpl(AmazonS3 s3Client) {
        this.s3Client = s3Client;
    }


    @Override
    public String uploadPictureToAwsS3(MultipartFile multipartFile, S3pictureModel s3pictureModel) throws FileUploadException, IOException {
        //convert multipartFile to File
        File file = new File(multipartFile.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(file)){
            fos.write(multipartFile.getBytes());

        }

        //generateFileName with uuid + file extension
        String fileName = generateFileNameByUUID() + "." + FilenameUtils.getExtension(multipartFile.getOriginalFilename());

        //upload file to s3
        try{
            PutObjectRequest request = new PutObjectRequest(bucketName, fileName, file);
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("plain/" + FilenameUtils.getExtension(multipartFile.getOriginalFilename()));
            metadata.addUserMetadata("Title", "File Upload - " + fileName);
            metadata.setContentLength(file.length());
            request.setMetadata(metadata);
            s3Client.putObject(request);
        }catch (Exception e){
            throw new FileUploadException("file upload failed");
        }finally {
            file.delete();
        }

        return fileName;
    }

    @Override
    public boolean deleteS3PictureByKey(String key) throws IOException, FileDeleteException {
        try {
            s3Client.deleteObject(bucketName, key);
        }catch (Exception e){
            throw new FileDeleteException("file delete failed");
        }
        return true;
    }


    public static String generateFileNameByUUID(){
        UUID uuid = UUID.randomUUID();

        return  uuid.toString();
    }
}
