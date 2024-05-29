package io.porko.widget.service;

import static io.porko.config.security.TestSecurityConfig.testMember;
import static io.porko.widget.controller.WidgetControllerTestHelper.widgetsResponse;
import static io.porko.widget.fixture.MemberWidgetFixture.modifyModifyMemberWidgetsOrderRequest;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.verify;

import io.porko.config.base.business.ServiceTestBase;
import io.porko.member.service.MemberService;
import io.porko.widget.controller.model.ModifyMemberWidgetsOrderRequest;
import io.porko.widget.repo.MemberWidgetRepo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

@DisplayName("Service:MemberWidget")
class MemberWidgetServiceTest extends ServiceTestBase {
    @Mock
    MemberService memberService;

    @Mock
    WidgetService widgetService;

    @Mock
    MemberWidgetRepo memberWidgetRepo;

    @InjectMocks
    MemberWidgetService memberWidgetService;

    @Test
    @DisplayName("회원 위젯 순서 변경")
    void reorderWidget() {
        // Given
        final Long memberId = 1L;
        given(memberService.findMemberById(memberId)).willReturn(testMember);
        given(widgetService.loadAllWidgets()).willReturn(widgetsResponse);

        willDoNothing().given(memberWidgetRepo).deleteByMemberId(memberId);
        given(memberWidgetRepo.saveAll(anyCollection())).willReturn(null);

        ModifyMemberWidgetsOrderRequest given = modifyModifyMemberWidgetsOrderRequest();

        // When
        memberWidgetService.reorderWidget(memberId, given);

        // Then
        verify(memberService).findMemberById(memberId);
        verify(widgetService).loadAllWidgets();
        verify(memberWidgetRepo).deleteByMemberId(memberId);
        verify(memberWidgetRepo).saveAll(anyCollection());
    }
}