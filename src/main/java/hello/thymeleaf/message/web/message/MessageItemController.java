package hello.thymeleaf.message.web.message;

import hello.thymeleaf.message.domain.item.MessageItem;
import hello.thymeleaf.message.domain.item.MessageItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/message/items")
@RequiredArgsConstructor
public class MessageItemController {

    private final MessageItemRepository messageItemRepository;

    @GetMapping
    public String items(Model model) {
        List<MessageItem> messageItems = messageItemRepository.findAll();
        model.addAttribute("items", messageItems);
        return "message/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        MessageItem messageItem = messageItemRepository.findById(itemId);
        model.addAttribute("item", messageItem);
        return "message/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new MessageItem());
        return "message/addForm";
    }

    @PostMapping("/add")
    public String addItem(@ModelAttribute MessageItem messageItem, RedirectAttributes redirectAttributes) {
        MessageItem savedMessageItem = messageItemRepository.save(messageItem);
        redirectAttributes.addAttribute("itemId", savedMessageItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/message/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        MessageItem messageItem = messageItemRepository.findById(itemId);
        model.addAttribute("item", messageItem);
        return "message/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute MessageItem messageItem) {
        messageItemRepository.update(itemId, messageItem);
        return "redirect:/message/items/{itemId}";
    }

}

