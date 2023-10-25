package hello.thymeleaf.upload.controller;

import hello.thymeleaf.upload.domain.UploadItemRepository;
import hello.thymeleaf.upload.file.FileStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String saveItem(@ModelAttribute ItemForm itemForm, RedirectAttributes redirectAttributes) {
        return "";
    }
}
