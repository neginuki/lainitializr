package world.sake.laspock

import javax.annotation.Resource

import spock.lang.Specification
import world.sake.dbflute.exbhv.MemberBhv
import world.sake.dbflute.exentity.Member
import world.sake.laspock.internal.TestInjectManager

@Laspock
class LaspockSpec extends Specification {

    @Resource
    MemberBhv memberBhv

    def "@Resource を指定したフィールドのDI"() {
        when:
        def name = memberBhv.selectEntityWithDeletedCheck {
            it.specify().columnMemberName()
            it.acceptPK(1)
        }.getMemberName()

        then:
        name == 'システム管理者'
    }

    def "自分で new したクラスのDI"() {
        given:
        def tim = new TestInjectManager()
        def bhv = tim.di new MemberBhv()

        when:
        def name = bhv.selectEntityWithDeletedCheck {
            it.specify().columnMemberName()
            it.acceptPK(1)
        }.getMemberName()

        then:
        name == 'システム管理者'
    }

    def "フィーチャメソッド単位のトランザクション"() {
        given:
        int maxId = memberBhv.selectScalar(Integer.class).max { it.specify().columnMemberId()}.orElse(0)

        def member = new Member()
        member.with {
            memberName = 'newMember'
            email = 'email@emai.com'
            password = 'pass123'
            statusCode = 'OK'
            displayOrder = 1
        }

        when:
        memberBhv.insert member

        then:
        member.memberId > maxId
    }
}