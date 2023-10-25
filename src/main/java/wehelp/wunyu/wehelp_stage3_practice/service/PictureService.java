package wehelp.wunyu.wehelp_stage3_practice.service;

import org.springframework.web.multipart.MultipartFile;
import wehelp.wunyu.wehelp_stage3_practice.exception.FileDeleteException;
import wehelp.wunyu.wehelp_stage3_practice.exception.FileUploadException;
import wehelp.wunyu.wehelp_stage3_practice.model.S3pictureModel;

import java.io.IOException;
import java.util.List;

public interface PictureService {
    boolean uploadPicture(MultipartFile file, S3pictureModel s3pictureModel) throws IOException, FileUploadException;
    List<S3pictureModel> getPicture() throws IOException;
    boolean deletePicture(String key) throws FileDeleteException, IOException;

}
