package  com.practice.shardingsphere.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.practice.shardingsphere.model.entity.BsMessage;
import com.practice.shardingsphere.mapper.BsMessageMapper;
import com.practice.shardingsphere.model.entity.BsMessageDetail;
import com.practice.shardingsphere.service.BsMessageDetailService;
import com.practice.shardingsphere.service.BsMessageService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 *
 */
@Service
@Log4j2
public class BsMessageServiceImpl extends ServiceImpl<BsMessageMapper, BsMessage>
    implements BsMessageService {


    @Autowired
    BsMessageDetailService messageDetailService;

    @Override
//    @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRES_NEW)
    public void testOnOpenTransactional() {

        String string = UUID.randomUUID().toString();

        BsMessage byId = getById(string);
        log.info("before: {} ", byId);

        BsMessage message = new BsMessage();
        message.setMsgId(System.currentTimeMillis());
        save(message);


        byId = getById(string);
        log.info("after: {} ", byId);

        List<BsMessageDetail> list = messageDetailService.list();
        log.info("after messageDetailService : {} ", byId);
    }



}




