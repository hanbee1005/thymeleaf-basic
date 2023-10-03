package hello.thymeleaf.validation.web.validation;

import hello.thymeleaf.validation.domain.item.ValidationItem;
import hello.thymeleaf.validation.domain.item.ValidationItemRepository;
import hello.thymeleaf.validation.web.validation.form.ItemSaveForm;
import hello.thymeleaf.validation.web.validation.form.ItemUpdateForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/validation/v4/items")
@RequiredArgsConstructor
public class ValidationItemControllerV4 {

    private final ValidationItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<ValidationItem> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "validation/v4/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        ValidationItem item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v4/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new ValidationItem());
        return "validation/v4/addForm";
    }

    @PostMapping("/add")
    public String addItem(@Valid @ModelAttribute("item") ItemSaveForm form, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        // 특정 필드가 아닌 복합 롤 검증
        if (form.getPrice() != null && form.getQuantity() != null) {
            int resultPrice = form.getPrice() * form.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }

        // 검증에 실패하여 다시 검증 폼으로
        if(bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            return "validation/v4/addForm";
        }

        ValidationItem savedItem = itemRepository.save(form.convertToValidationItem());
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v4/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        ValidationItem item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v4/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @Valid @ModelAttribute("item") ItemUpdateForm form, BindingResult bindingResult) {
        // 특정 필드가 아닌 복합 롤 검증
        if (form.getPrice() != null && form.getQuantity() != null) {
            int resultPrice = form.getPrice() * form.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }

        // 검증에 실패하여 다시 검증 폼으로
        if(bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            return "validation/v4/editForm";
        }

        itemRepository.update(itemId, form.convertToValidationItem());
        return "redirect:/validation/v4/items/{itemId}";
    }

}

