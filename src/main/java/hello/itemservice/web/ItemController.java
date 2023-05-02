package hello.itemservice.web;

import hello.itemservice.domain.Item;
import hello.itemservice.repository.ItemSearchCond;
import hello.itemservice.repository.ItemUpdateDto;
import hello.itemservice.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/items") // RequestMappingHandlerMapping , RequestMappingHandlerAdapter = 핸들러 매핑과 어댑터
@RequiredArgsConstructor
@Slf4j
public class ItemController {

    private final ItemService itemService;

    /**
     *  테스트
     */
    @GetMapping("/hello")
    public String hello(){
        return "boards/hello";
    }

    @GetMapping("/member")
    public String member(){return "addMemberForm";}

    @GetMapping
    public String items(@ModelAttribute("itemSearch") ItemSearchCond itemSearch, Model model) {
        List<Item> items = itemService.findItems(itemSearch);
        model.addAttribute("items", items);
        return "items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemService.findById(itemId).get();
        model.addAttribute("item", item);
        return "item";
    }

    @GetMapping("/add")
    public String addForm() {
        return "addForm";
    }

    @PostMapping("/add")
    public String addItem(@ModelAttribute Item item, RedirectAttributes redirectAttributes) {
        Item savedItem = itemService.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemService.findById(itemId).get();
        model.addAttribute("item", item);
        return "editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute ItemUpdateDto updateParam) {
        itemService.update(itemId, updateParam);
        return "redirect:/items/{itemId}";
    }

    @GetMapping("/{itemId}/item/delete")
    public String delete(@PathVariable Long itemId,RedirectAttributes redirectAttributes) {
        log.info("컨트롤러 진입");
        itemService.delete(itemId);
        redirectAttributes.addAttribute("status","deleted");

        // redirect : 상품 수정은 마지막에 뷰 템플릿을 호출하는 대신 상품 상세 화면으로 이동
        return "redirect:/items";
    }

    /**  테스트
     */
    @GetMapping("/test/write")
    public String openPostWrite(Model model) {
        String title = "제목",
                content = "내용",
                writer = "홍길동";

        model.addAttribute("t", title);
        model.addAttribute("c", content);
        model.addAttribute("w", writer);
        return "test/write";
    }
}
