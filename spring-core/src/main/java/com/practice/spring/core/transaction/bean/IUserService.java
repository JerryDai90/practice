package com.practice.spring.core.transaction.bean;

import java.util.List;

/**
 * @author jerry dai
 * @date 2023-07-11- 09:47
 */
public interface IUserService {

    List query();

    void update();

    void delete();

}
