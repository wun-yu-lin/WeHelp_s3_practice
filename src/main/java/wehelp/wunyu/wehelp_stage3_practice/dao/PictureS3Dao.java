package wehelp.wunyu.wehelp_stage3_practice.dao;

import org.springframework.web.multipart.MultipartFile;
import wehelp.wunyu.wehelp_stage3_practice.exception.FileDeleteException;
import wehelp.wunyu.wehelp_stage3_practice.exception.FileUploadException;
import wehelp.wunyu.wehelp_stage3_practice.model.S3pictureModel;

import java.io.IOException;

public interface PictureS3Dao {
    String uploadPictureToAwsS3(MultipartFile multipartFile, S3pictureModel s3pictureModel) throws FileUploadException, IOException;
    boolean deleteS3PictureByKey(String key) throws IOException, FileDeleteException;
}
