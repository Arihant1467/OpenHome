package com.cmpe275.OpenHome.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PaymentsServiceImpl implements PaymentsService {



}
