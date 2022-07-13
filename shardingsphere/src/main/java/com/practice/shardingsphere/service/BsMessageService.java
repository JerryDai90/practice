package  com.practice.shardingsphere.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.practice.shardingsphere.model.entity.BsMessage;

/**
 *
 */
public interface BsMessageService extends IService<BsMessage> {

    /**
     * 测试使用事务，看看是否都在 master中读写
     */
    void testOnOpenTransactional();

}
