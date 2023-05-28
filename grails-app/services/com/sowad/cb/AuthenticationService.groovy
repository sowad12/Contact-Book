package com.sowad.cb

import grails.gorm.transactions.Transactional

@Transactional
class AuthenticationService {

    private static final String AUTHORIZED = "AUTHORIZED"

    def setMemberAuthorization(Member member) {
        def authorization = [isLoggedIn: true, member: member]
        AppUtil.getAppSession()[AUTHORIZED] = authorization
    }

    def doLogin(String email, String password){
        password = password.encodeAsMD5()
        Member member = Member.findByEmailAndPassword(email, password)

        if (member){
            setMemberAuthorization(member)
            return true
        }
        return false
    }

    boolean isAuthenticated(){
        def authorization = AppUtil.getAppSession()[AUTHORIZED]
        if (authorization && authorization.isLoggedIn){
            return true
        }
        return false
    }


    def getMember(){
        def authorization = AppUtil.getAppSession()[AUTHORIZED]
        return authorization?.member
    }


    def getMemberName(){
        def member = getMember()
        return "${member.firstName} ${member.lastName}"
    }

}
