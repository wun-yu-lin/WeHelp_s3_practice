package wehelp.wunyu.wehelp_stage3_practice.dao;

import wehelp.wunyu.wehelp_stage3_practice.model.S3pictureModel;

import java.io.IOException;
import java.util.List;

public interface PictureRdbDao {
    boolean insertPictureToRdb(S3pictureModel s3pictureModel) throws IOException;
    List<S3pictureModel>  getPictureFromRdb() throws IOException;
    S3pictureModel getPictureFromRdbById() throws IOException;
    boolean deleteRdbPictureById(int id) throws IOException;
}
