package hello.thymeleaf.upload.controller;

import hello.thymeleaf.upload.domain.Item;
import hello.thymeleaf.upload.domain.UploadFile;
import hello.thymeleaf.upload.domain.UploadItemRepository;
import hello.thymeleaf.upload.file.FileStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

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
}
