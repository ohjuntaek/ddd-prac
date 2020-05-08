package ex.marketboro.dddprac.orders.interfaces;

import ex.marketboro.dddprac.orders.application.OrderService;
import ex.marketboro.dddprac.orders.domain.Orders;
import ex.marketboro.dddprac.orders.dto.OrderDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/order/{seller}")
    public ResponseEntity<?> order(@RequestParam("memberloginid") String memberLoginId, @PathVariable String seller, @RequestBody OrderDTO orderDTO) {
        String body = orderService.order(memberLoginId, seller, orderDTO);
        return ResponseEntity.ok().body(body);
    }

    @GetMapping("/order")
    public ResponseEntity<?> getOrder(@RequestParam("memberloginid") String memberLoginId) {
        List<Orders> body = orderService.getOrdersOfMember(memberLoginId);
        return ResponseEntity.ok().body(body);
    }

    @GetMapping("/order/{orderId}/approve")
    public ResponseEntity<?> approveOrder(@RequestParam("memberloginid") String memberLoginId, @PathVariable Long orderId) {
        boolean isApproved = orderService.approveOrder(memberLoginId, orderId);
        return ResponseEntity.ok().body(isApproved ? "approved" : "not approved");
    }


}
