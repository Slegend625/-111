package com.nowcoder.community.dao;

import org.springframework.stereotype.Repository;

@Repository("a")
public class AlphaDaoImpl implements AlphaDao{

    @Override
    public void select() {
        System.out.println("Hello");
    }
}
