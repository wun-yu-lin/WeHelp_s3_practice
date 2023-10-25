package wehelp.wunyu.wehelp_stage3_practice.rowMapper;

import org.springframework.jdbc.core.RowMapper;
import wehelp.wunyu.wehelp_stage3_practice.model.S3pictureModel;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PictureS3RowMapper implements RowMapper<S3pictureModel> {
    @Override
    public S3pictureModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        S3pictureModel s3pictureModel = new S3pictureModel();
        s3pictureModel.setId(rs.getInt("id"));
        s3pictureModel.setMessage(rs.getString("message"));
        s3pictureModel.setKey(rs.getString("key"));

        return s3pictureModel;
    }
}
