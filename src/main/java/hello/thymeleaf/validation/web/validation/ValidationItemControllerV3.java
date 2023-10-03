package hello.thymeleaf.validation.web.validation;

import hello.thymeleaf.validation.domain.item.SaveCheck;
import hello.thymeleaf.validation.domain.item.UpdateCheck;
import hello.thymeleaf.validation.domain.item.ValidationItem;
import hello.thymeleaf.validation.domain.item.ValidationItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/validation/v3/items")
@RequiredArgsConstructor
public class ValidationItemControllerV3 {

    private final ValidationItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<ValidationItem> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "validation/v3/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        ValidationItem item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v3/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new ValidationItem());
        return "validation/v3/addForm";
    }

//    @PostMapping("/add")
    public String addItem(@Valid @ModelAttribute("item") ValidationItem item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        // 특정 필드가 아닌 복합 롤 검증
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }

        // 검증에 실패하여 다시 검증 폼으로
        if(bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            return "validation/v3/addForm";
        }

        ValidationItem savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v3/items/{itemId}";
    }

    @PostMapping("/add")
    public String addItemV2(@Validated(SaveCheck.class) @ModelAttribute("item") ValidationItem item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        // 특정 필드가 아닌 복합 롤 검증
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }

        // 검증에 실패하여 다시 검증 폼으로
        if(bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            return "validation/v3/addForm";
        }

        ValidationItem savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v3/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        ValidationItem item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v3/editForm";
    }

//    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @Valid @ModelAttribute("item") ValidationItem item, BindingResult bindingResult) {
        // 특정 필드가 아닌 복합 롤 검증
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }

        // 검증에 실패하여 다시 검증 폼으로
        if(bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            return "validation/v3/editForm";
        }

        itemRepository.update(itemId, item);
        return "redirect:/validation/v3/items/{itemId}";
    }

    @PostMapping("/{itemId}/edit")
    public String editV2(@PathVariable Long itemId, @Validated(UpdateCheck.class) @ModelAttribute("item") ValidationItem item, BindingResult bindingResult) {
        // 특정 필드가 아닌 복합 롤 검증
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }

        // 검증에 실패하여 다시 검증 폼으로
        if(bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            return "validation/v3/editForm";
        }

        itemRepository.update(itemId, item);
        return "redirect:/validation/v3/items/{itemId}";
    }

}

