package com.practice.shardingsphere.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.practice.shardingsphere.model.entity.BsMessage;
import com.practice.shardingsphere.service.BsMessageService;
import org.apache.shardingsphere.api.hint.HintManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@RestController
public class TestController {

    @Autowired
    BsMessageService messageService;

    @GetMapping("/test/{userId}")
    public BsMessage test(@PathVariable("userId") Integer userId){

//        List<BsMessage> list = messageService.list();


//        String string = UUID.randomUUID().toString();

        BsMessage message = new BsMessage();
//        message.setMsgId(System.currentTimeMillis());


        message.setUserId(userId);

        messageService.save(message);


//        BsMessage byId = messageService.getById(message.getMsgId());
//
//        System.out.println(byId);

//        HintManager



        return null;
    }


    @GetMapping("/testOnOpenTransactional")
    public String testOnOpenTransactional(){

        messageService.testOnOpenTransactional();

        return "byId";
    }


    @GetMapping("/list/{userId}")
    public List<BsMessage> listByUserId(@PathVariable("userId") Integer userId){

        QueryWrapper<BsMessage> queryWrapper = new QueryWrapper<>();

        queryWrapper.lambda().eq(BsMessage::getUserId, userId);

        List<BsMessage> list = messageService.list(queryWrapper);

        return list;
    }







}
