package ex.marketboro.dddprac.member.interfaces;

import ex.marketboro.dddprac.member.application.MemberService;
import ex.marketboro.dddprac.member.domain.Goods;
import ex.marketboro.dddprac.member.dto.GoodsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/goods")
    public ResponseEntity<Goods> createGoods(@RequestParam String memberLoginId, @RequestBody GoodsDTO goodsDTO) {
        Goods body = memberService.createGoodsOfMember(memberLoginId, goodsDTO);
        return ResponseEntity.ok().body(body);
    }

    @GetMapping("/goods")
    public ResponseEntity<Map<String, Goods>> getGoodsMapOfMember(@RequestParam String memberLoginId) {
        Map<String, Goods> body = memberService.getGoodsMapOfMember(memberLoginId);
        return ResponseEntity.ok().body(body);
    }

    @GetMapping("/goods/{code}")
    public ResponseEntity<Goods> getGoodsOfMember(@RequestParam String memberLoginId, @PathVariable String code) {
        Goods body = memberService.getGoodsOfMemberByCode(memberLoginId, code);
        return ResponseEntity.ok().body(body);
    }

    @PutMapping("/goods")
    public ResponseEntity<Goods> updateGoods(@RequestParam String memberLoginId, @RequestBody GoodsDTO goodsDTO) {
        Goods body = memberService.updateGoods(memberLoginId, goodsDTO);
        return ResponseEntity.ok().body(body);
    }

    @DeleteMapping("/goods/{code}")
    public ResponseEntity<Boolean> deleteGoods(@RequestParam String memberLoginId, @PathVariable String code) {
        boolean body = memberService.deleteGoodsOfMemberByCode(memberLoginId, code);
        return ResponseEntity.ok().body(body);
    }

}
