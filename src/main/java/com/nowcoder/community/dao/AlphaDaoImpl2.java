package com.nowcoder.community.dao;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class AlphaDaoImpl2 implements AlphaDao{

    @Override
    public void select() {
        System.out.println("2222");
    }
}
