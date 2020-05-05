package ex.marketboro.dddprac.member.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findMemberByLoginId(String loginId);

    @Query(nativeQuery = true, value = "SELECT g.name, g.category FROM MEMBER_GOODS_MAP g WHERE GOODS_MAP_KEY = :code")
    Collection<GoodsOnly> findGoodsByCode(@Param("code") String code);
}
