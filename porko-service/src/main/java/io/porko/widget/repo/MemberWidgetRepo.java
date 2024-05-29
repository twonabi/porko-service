package io.porko.widget.repo;

import io.porko.widget.domain.MemberWidget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface MemberWidgetRepo extends JpaRepository<MemberWidget, Long> {
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query("delete from MemberWidget m where m.member.id = :memberId")
    void deleteByMemberId(Long memberId);
}
