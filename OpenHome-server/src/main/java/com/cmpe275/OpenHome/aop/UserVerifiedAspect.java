package com.cmpe275.OpenHome.aop;

import com.cmpe275.OpenHome.dao.UserDAO;
import com.cmpe275.OpenHome.model.Postings;
import com.cmpe275.OpenHome.model.Reservation;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;


import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import static com.mysql.cj.conf.PropertyKey.logger;

@Aspect
@Configuration
public class UserVerifiedAspect {

    @Autowired
    private UserDAO userDao;

    @Transactional
    @Before("execution(public * com.cmpe275.OpenHome.service.PostingsService.save(..))")
    public void verifyUser(JoinPoint joinpoint) throws Exception
    {

        Object[] args = joinpoint.getArgs();
        System.out.println("Argument"+args[0]);
        Postings p = (Postings) args[0];
        System.out.println("Posting email"+p.getUserId());
        String email=p.getUserId();

        System.out.println("email is"+email);

        if(!userDao.isVerifiedOrNot(email))
        {
            System.out.println("ExceptionTime");
            throw new Exception("Please verify your email account before posting");
        }

    }

    @Transactional
    @Before("execution(public * com.cmpe275.OpenHome.service.ReservationService.save(..))")
    public void verifyUserService(JoinPoint joinpoint) throws Exception
    {


        Object[] args = joinpoint.getArgs();
        System.out.println("Argument"+args[0]);
        Reservation r = (Reservation) args[0];
        System.out.println("Posting email"+r.getTenantEmailId());
        String email=r.getTenantEmailId();
        System.out.println("email is"+email);

        if(!userDao.isVerifiedOrNot(email))
        {
            System.out.println("ExceptionTime");
            throw new Exception("Please verify your email account before reserving");
        }



    }




}
