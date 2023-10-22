package hello.thymeleaf.upload.domain;

import lombok.Data;

@Data
public class UploadFile {
    private String uploadFileName;  // 고객이 업로드한 파일명
    private String storeFileName;  // 서버 내부에서 관리하는 파일명
}
