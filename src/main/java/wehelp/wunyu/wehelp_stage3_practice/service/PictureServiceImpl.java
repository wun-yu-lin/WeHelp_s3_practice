package wehelp.wunyu.wehelp_stage3_practice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import wehelp.wunyu.wehelp_stage3_practice.dao.PictureRdbDao;
import wehelp.wunyu.wehelp_stage3_practice.dao.PictureS3Dao;
import wehelp.wunyu.wehelp_stage3_practice.exception.FileDeleteException;
import wehelp.wunyu.wehelp_stage3_practice.exception.FileUploadException;
import wehelp.wunyu.wehelp_stage3_practice.model.S3pictureModel;

import java.io.IOException;
import java.util.List;

@Component
public class PictureServiceImpl implements PictureService{

    private final PictureS3Dao pictureDao;
    private final PictureRdbDao pictureRdbDao;

    @Value("${aws.cloudFront.endpoint}")
    private String awsCloudFrontEndpoint;

    @Autowired
    public PictureServiceImpl(PictureS3Dao pictureDao, PictureRdbDao pictureRdbDao) {
        this.pictureDao = pictureDao;
        this.pictureRdbDao = pictureRdbDao;
    }

    @Transactional
    @Override
    public boolean uploadPicture(MultipartFile file, S3pictureModel s3pictureModel) throws IOException, FileUploadException {
        String fileName= pictureDao.uploadPictureToAwsS3(file, s3pictureModel);
        if (fileName == null){
            throw new FileUploadException("file upload failed");
        }
        s3pictureModel.setKey(fileName);
        boolean isInsertSuccess =  pictureRdbDao.insertPictureToRdb(s3pictureModel);
        if (!isInsertSuccess){
            throw new FileUploadException("file upload failed");
        }

        return true;
    }

    @Override
    public List<S3pictureModel> getPicture() throws IOException {

        List<S3pictureModel> s3pictureModelList = pictureRdbDao.getPictureFromRdb();
        s3pictureModelList.forEach(element -> {
            element.setImgSrc(awsCloudFrontEndpoint + element.getKey());
        });
        return s3pictureModelList;
    }

    @Transactional
    @Override
    public boolean deletePicture(String key) throws FileDeleteException, IOException {
        boolean isRdbDeleteSuccess = pictureRdbDao.deleteRdbPictureByKey(key);
        boolean isS3DeleteSuccess = pictureDao.deleteS3PictureByKey(key);

        if (!isS3DeleteSuccess || !isRdbDeleteSuccess){
            throw new FileDeleteException("file delete failed");
        }

        return true;
    }


}
