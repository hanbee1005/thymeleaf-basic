package hello.thymeleaf.upload.controller;

import hello.thymeleaf.upload.domain.Item;
import hello.thymeleaf.upload.domain.UploadFile;
import hello.thymeleaf.upload.domain.UploadItemRepository;
import hello.thymeleaf.upload.file.FileStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ItemController {
    private final UploadItemRepository uploadItemRepository;
    private final FileStore fileStore;

    @GetMapping("/item/new")
    public String newItem(@ModelAttribute ItemForm itemForm) {
        return "/upload/item-form";
    }

    @PostMapping("/item/new")
    public String saveItem(@ModelAttribute ItemForm itemForm, RedirectAttributes redirectAttributes) throws IOException {
        UploadFile storeAttachFile = fileStore.storeFile(itemForm.getAttachFile());
        List<UploadFile> storeImageFiles = fileStore.storeFiles(itemForm.getImageFiles());

        // 데이터베이스에 저장
        Item item = new Item();
        item.setItemName(itemForm.getItemName());
        item.setAttachFile(storeAttachFile);
        item.setImageFiles(storeImageFiles);

        uploadItemRepository.save(item);

        redirectAttributes.addAttribute("itemId", item.getId());

        return "redirect:/items/{itemId}";
    }

    @GetMapping("/items/{id}")
    public String items(@PathVariable Long id, Model model) {
        Item item = uploadItemRepository.findById(id);
        model.addAttribute("item", item);
        return "/upload/item-view";
    }

    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadFile(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + fileStore.getFullPath(filename));
    }

    @GetMapping("/attach/{itemId}")
    public ResponseEntity<Resource> downloadAttach(@PathVariable Long itemId) throws MalformedURLException {
        Item item = uploadItemRepository.findById(itemId);
        String storeFileName = item.getAttachFile().getStoreFileName();
        String uploadFileName = item.getAttachFile().getUploadFileName();

        UrlResource urlResource = new UrlResource("file:" + fileStore.getFullPath(storeFileName));

        log.info("uploadFileName={}", uploadFileName);

        String encodedUploadFileName = UriUtils.encode(uploadFileName, StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=\"" + encodedUploadFileName + "\"";

        return ResponseEntity.ok()
                .header(CONTENT_DISPOSITION, contentDisposition)
                .body(urlResource);
    }
}
