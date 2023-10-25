package wehelp.wunyu.wehelp_stage3_practice.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import wehelp.wunyu.wehelp_stage3_practice.model.S3pictureModel;
import wehelp.wunyu.wehelp_stage3_practice.rowMapper.PictureS3RowMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PictureRdbDaoImpl implements PictureRdbDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @Autowired
    public PictureRdbDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public boolean insertPictureToRdb(S3pictureModel s3pictureModel) throws IOException {
        String sqlString = "INSERT INTO wehelp_s3.picture (`message`, `key`) VAlUES (:message, :key)";
        Map<String, Object> map = new HashMap<>();
        map.put("message", s3pictureModel.getMessage());
        map.put("key", s3pictureModel.getKey());
        boolean isInsertSuccess = namedParameterJdbcTemplate.update(sqlString, map) > 0;

        return isInsertSuccess;
    }

    @Override
    public List<S3pictureModel> getPictureFromRdb() throws IOException {

        String sqlString = "Select p.id, p.`key`, p.`message` from wehelp_s3.picture p order by p.creatTime desc;";
        Map<String, Object> map = new HashMap<>();
        return namedParameterJdbcTemplate.query(sqlString, map, new PictureS3RowMapper());

    }

    @Override
    public S3pictureModel getPictureFromRdbById() throws IOException {
        return null;
    }

    @Override
    public boolean deleteRdbPictureById(int id) throws IOException {
        return false;
    }
}
