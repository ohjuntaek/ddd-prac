# DDD prac

domain driven design 연습 repo

## 요구사항 정리

간단 주문 관리 시스템

#### 회원(점포)

- 우리에게 돈을 지불한 사용자에게 ID와 PW를 발급해 회원으로 등록한다.
- ID와 PW로 로그인을 하면 우리 서비스를 이용할 수 있게 해준다.
- 회원은 점포에 대한 정보인 점포 이름, 주소를 하나 가진다.
- 회원은 자신이 소유한 상품 정보를 생성할 수 있다.
- 회원은 다른 회원이 소유한 상품을 조회할 수 있다.
- 회원은 다른 회원에게 필요한 상품들을 주문할 수 있다.
- 이 때 주문한 회원을 구매자, 주문받은 회원을 판매자라고 한다.
- 판매자는 구매자의 주문을 승낙할 수 있고 거절할 수 있다.
- 주문을 거절하면 다시 승낙할 수 없으며, 주문이 끝나게 된다.
- 승낙되면 주문이 완료되고, 상품이 판매된다.
- 구매된 상품에 대한 정보는 관리될 수 있다. (수정, 조회, 삭제가 가능하다.)

#### 상품

- 회원이 가지고 있으며, 주문을 통해 거래될 수 있는 것을 상품이라고 한다.
- 상품은 상품이름, 상품구분, 상품코드에 대한 정보를 가진다.

## 용어 사전

| 한글명 | 영문명 | 설명 |
| --- | --- | --- |
| 회원 | Member | 우리에게 돈을 지불해 ID와 PW가 있는 사용자를 뜻한다. |
| 점포 | Shop | 회원이 관리하는 점포, 하나만 가질 수 있음 |
| 상품 | Goods | 회원이 관리하는 것, 주문을 통해 거래될 수 있음 |
| 주문 | Orders | 회원이 상품을 구매하기 위해 하는 행위 |
| 주문 승낙 | Approve Order | 주문에 대해 승낙해 상품을 판매 |
| 주문 거절 | Decline Order | 주문에 대해 거절 |
| 구매자 | Buyer | 다른 회원에게 주문을 요청한 회원 |
| 판매자 | Seller | 주문을 요청받은 회원 |
| 상품 구매 | Buy Goods | 회원이 생성하거나 주문을 통해 상품을 가지게 됨 |
| 상품 판매 | Sell Goods | 상품의 소유권을 구매자에게 넘김 |
| 상품 소유 | Own Goods | 소유한 회원은 상품을 관리할 수 있다. |
| 주문 상품 | OrderLine | 주문한 상품들 |

## 간단 도메인 모델링

#### Member(회원)

- (member_id, member_login_id, member_login_password, member_name)

- 로그인한다. (JWT, 추후 구현)
- 회원 정보 조회 (수정, 삭제? 추후 구현)

- [x] 생성 테스트

#### Shop(점포)

- Member 가 가지는 점포 정보
- (shop_name, shop_address)
- Member와 1:1

- [x] 생성 테스트
- [x] 회원은 점포 정보를 가진다.
- [x] shop_name은 10자가 넘으면 안된다.

#### Goods(상품)

- (goods_name, goods_code, goods_category)
- FK : member_id(소유)
- 회원 (1 : N) 상품

- [x] 생성 테스트
- [x] 동등성 테스트
- [x] 회원이 상품을 생성한다.
- [ ] 회원이 소유한 상품을 코드별로 조회, 수정, 삭제한다.

- [ ] 회원이 소유하지 않은 상품을 조회한다.
- [ ] 회원이 소유하지 않은 상품은 수정, 삭제할 수 없다.

#### Orders(주문)

- (order_id)
- 주문 (1 : N) 상품

- 회원이 상품을 선택해 주문한다.
- 판매자가 주문을 거절한다.
- 판매자가 주문을 승낙한다.

### OrderLines(주문 상품)

- (order_id, goods_id)

- 승낙된 주문의 상품들을 조회한다.
- 승낙된 주문의 상품들을 구매자가 소유한다.

- 거절한 주문의 상품들을 구매자가 소유하지 않는다.


## 참고 링크

- https://github.com/wotjd243/fastcampus.ddd-alone


## TO-DO

- [ ] Shop 값 객체








