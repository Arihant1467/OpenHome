package com.cmpe275.OpenHome.service;

import com.cmpe275.OpenHome.controller.MailServiceController;
import com.cmpe275.OpenHome.dao.ReservationDAO;
import com.cmpe275.OpenHome.dao.UserDAO;
import com.cmpe275.OpenHome.enums.LoginType;
import com.cmpe275.OpenHome.enums.UserType;
import com.cmpe275.OpenHome.model.Mail;
import com.cmpe275.OpenHome.model.User;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import java.util.*;


@Service
//@Transactional(readOnly = true)
public class UserServiceImpl implements UserService{


    @Autowired
    private UserDAO userDao;

    @Autowired
    private JavaMailSender mailSenderObj;

    @Autowired
    private MailServiceController mailServiceController;



    @Override
    @Transactional
    public List<User> list() {
        return userDao.list();
    }

    @Transactional
    public User login(User user) throws Exception {
        return userDao.login(user);
    }

    @Transactional
    public User save(User user) throws Exception{
        System.out.println("In User service of signup");
        //user.setLogintype(LoginType.REGULAR);
        String email = user.getEmail();
        if(email.contains("@sjsu.edu"))
            user.setUsertype(UserType.HOST);
        else
            user.setUsertype(UserType.GUEST);
        User  u = userDao.save(user);

        String emailText = "Click here to verify your account http://localhost:3000/verifyAccount/"+user.getEmail();
        String emailSubject = "Verify your mailAddress";
        Mail mail = new Mail(emailText, emailSubject,user.getEmail() );
        mailServiceController.addToQueue(mail);

        return u;
    }

    @Transactional
    public User verify(String user)
    {

        User verifiedUser = userDao.verify(user);
        return verifiedUser;
    }



}
