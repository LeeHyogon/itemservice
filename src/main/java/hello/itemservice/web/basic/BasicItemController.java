package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemDto;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {
    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm(){
        return "basic/addForm";
    }

    //@PostMapping("/add")
    public String save(ItemDto itemDto,Model model){
        Item item = new Item(itemDto.getItemName(), itemDto.getPrice(), itemDto.getQuantity());
        itemRepository.save(item);
        model.addAttribute("item", item);
        return "basic/item";
    }
    //@PostMapping("/add")
    public String saveV2(@ModelAttribute("item") Item item,Model model){
        itemRepository.save(item);
        //model.addAttribute("item", item); //자동 추가,생략 가능
        return "basic/item";
    }

    //@PostMapping("/add")
    public String saveV3(@ModelAttribute Item item){
        //모델이름 Item->item으로 자동변환.
        itemRepository.save(item);
        //model.addAttribute("item", item); //자동 추가,생략 가능
        return "basic/item";
    }
    //@PostMapping("/add")
    public String saveV4(Item item){
        itemRepository.save(item);
        return "basic/item";
    }
    //@PostMapping("/add")
    public String saveV5(@ModelAttribute Item item){
        //모델이름 Item->item으로 자동변환.
        itemRepository.save(item);
        //model.addAttribute("item", item); //자동 추가,생략 가능
        return "redirect:/basic/items/"+item.getId();
    }
    @PostMapping("/add")
    public String saveV6(@ModelAttribute Item item, RedirectAttributes redirectAttributes){
        //모델이름 Item->item으로 자동변환.
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/basic/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "/basic/editForm";
    }
    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId,@ModelAttribute Item item) {
        itemRepository.update(itemId,item);
        return "redirect:/basic/items/{itemId}";
    }


    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {

        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));
    }

}
