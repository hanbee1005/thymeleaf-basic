package hello.thymeleaf.message.domain.item;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MessageItemRepository {

    private static final Map<Long, MessageItem> store = new HashMap<>(); //static
    private static long sequence = 0L; //static

    public MessageItem save(MessageItem messageItem) {
        messageItem.setId(++sequence);
        store.put(messageItem.getId(), messageItem);
        return messageItem;
    }

    public MessageItem findById(Long id) {
        return store.get(id);
    }

    public List<MessageItem> findAll() {
        return new ArrayList<>(store.values());
    }

    public void update(Long itemId, MessageItem updateParam) {
        MessageItem findMessageItem = findById(itemId);
        findMessageItem.setItemName(updateParam.getItemName());
        findMessageItem.setPrice(updateParam.getPrice());
        findMessageItem.setQuantity(updateParam.getQuantity());
    }

    public void clearStore() {
        store.clear();
    }

}
