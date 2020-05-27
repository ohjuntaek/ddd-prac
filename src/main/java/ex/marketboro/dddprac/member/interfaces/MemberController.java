package ex.marketboro.dddprac.member.interfaces;

import ex.marketboro.dddprac.member.application.MemberService;
import ex.marketboro.dddprac.member.domain.Goods;
import ex.marketboro.dddprac.member.dto.GoodsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/goods")
    public ResponseEntity<GoodsDTO> createOwnGoods(@RequestParam("member_login_id") String memberLoginId, @RequestBody GoodsDTO goodsDTO) {
        GoodsDTO body = memberService.createGoodsOfMember(memberLoginId, goodsDTO);
        return ResponseEntity.ok().body(body);
    }

    @GetMapping("/goods")
    public ResponseEntity<List<GoodsDTO>> getGoodsMapOfMember(@RequestParam("member_login_id") String memberLoginId) {
        List<GoodsDTO> body = memberService.getGoodsListOfMember(memberLoginId);
        return ResponseEntity.ok().body(body);
    }

    @GetMapping("/goods/{code}")
    public ResponseEntity<GoodsDTO> getGoodsOfMemberByCode(@RequestParam("member_login_id") String memberLoginId, @PathVariable String code) {
        GoodsDTO body = memberService.getGoodsByCode(memberLoginId, code);

        return ResponseEntity.ok().body(body);
    }

    @GetMapping("/{otherMemberLoginId}/goods")
    public ResponseEntity<Map<String, Goods>> getGoodOfOtherMember(@RequestParam("member_login_id") String memberLoginId, @PathVariable String otherMemberLoginId) {
        Map<String, Goods> body = memberService.getGoodsMapOfOtherMember(memberLoginId, otherMemberLoginId);
        return ResponseEntity.ok().body(body);
    }

    @PutMapping("/goods")
    public ResponseEntity<Goods> updateGoods(@RequestParam("member_login_id") String memberLoginId, @RequestBody GoodsDTO goodsDTO) {
        Goods body = memberService.updateGoods(memberLoginId, goodsDTO);
        return ResponseEntity.ok().body(body);
    }

    @DeleteMapping("/goods/{code}")
    public ResponseEntity<Boolean> deleteGoods(@RequestParam("member_login_id") String memberLoginId, @PathVariable String code) {
        boolean body = memberService.deleteGoodsOfMemberByCode(memberLoginId, code);
        return ResponseEntity.ok().body(body);
    }
}
