package hello.thymeleaf.login.web.item;

import hello.thymeleaf.login.domain.item.LoginItem;
import hello.thymeleaf.login.domain.item.LoginItemRepository;
import hello.thymeleaf.login.web.item.form.LoginItemSaveForm;
import hello.thymeleaf.login.web.item.form.LoginItemUpdateForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/login/items")
@RequiredArgsConstructor
public class LoginItemController {

    private final LoginItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<LoginItem> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "login/items/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        LoginItem item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "login/items/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new LoginItem());
        return "login/items/addForm";
    }

    @PostMapping("/add")
    public String addItem(@Validated @ModelAttribute("item") LoginItemSaveForm form, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        //특정 필드 예외가 아닌 전체 예외
        if (form.getPrice() != null && form.getQuantity() != null) {
            int resultPrice = form.getPrice() * form.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "login/items/addForm";
        }

        //성공 로직
        LoginItem savedItem = itemRepository.save(form.convertToLoginItem());
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/login/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        LoginItem item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "login/items/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @Validated @ModelAttribute("item") LoginItemUpdateForm form, BindingResult bindingResult) {

        //특정 필드 예외가 아닌 전체 예외
        if (form.getPrice() != null && form.getQuantity() != null) {
            int resultPrice = form.getPrice() * form.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "login/items/editForm";
        }

        itemRepository.update(itemId, form.convertToLoginItem());
        return "redirect:/login/items/{itemId}";
    }

}
