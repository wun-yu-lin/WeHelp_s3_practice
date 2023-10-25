package wehelp.wunyu.wehelp_stage3_practice.controller;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import wehelp.wunyu.wehelp_stage3_practice.exception.FileDownloadException;
import wehelp.wunyu.wehelp_stage3_practice.exception.FileEmptyException;
import wehelp.wunyu.wehelp_stage3_practice.exception.FileUploadException;
import wehelp.wunyu.wehelp_stage3_practice.model.S3pictureModel;
import wehelp.wunyu.wehelp_stage3_practice.service.PictureService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/picture")
public class PictureController {

    private final PictureService pictureService;
    @Autowired
    public PictureController(PictureService pictureService) {
        this.pictureService = pictureService;
    }

    //upload picture api
    @PostMapping("/upload")
    public ResponseEntity<String> uploadPicture(
            @RequestParam("img") MultipartFile multipartFile,
            @RequestParam String message
            ) throws FileEmptyException, FileUploadException, IOException {
        if (multipartFile.isEmpty()) {
            throw new FileEmptyException("file is empty");
        }
        List<String> allowedFileExtensions = new ArrayList<>(Arrays.asList("png", "jpg", "jpeg", "gif", "svg"));
        if (!allowedFileExtensions.contains(FilenameUtils.getExtension(multipartFile.getOriginalFilename()))){
            throw new FileUploadException("file is not valid");
        }

        //call service to upload picture
        S3pictureModel s3pictureModel = new S3pictureModel();
        s3pictureModel.setMessage(message);
        boolean isUploadSuccess =  pictureService.uploadPicture(multipartFile, s3pictureModel);
        if (!isUploadSuccess){
            throw new FileUploadException("file upload failed");
        }


        return ResponseEntity.status(HttpStatus.OK).body("picture upload success");
    }

    @GetMapping("/")
    public ResponseEntity<List<S3pictureModel>> getPicture() throws FileDownloadException, IOException {
        List<S3pictureModel> s3pictureModelList = pictureService.getPicture();
        if (s3pictureModelList.isEmpty()) {
            throw new FileDownloadException("no picture found");
        }

        return ResponseEntity.status(HttpStatus.OK).body(s3pictureModelList);
    }





}
