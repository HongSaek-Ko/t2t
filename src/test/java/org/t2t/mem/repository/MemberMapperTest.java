package org.t2t.mem.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.t2t.mem.dto.MileDTO;

import java.util.List;

@SpringBootTest

class MemberMapperTest {
    @Autowired
    private MemberMapper mapper;
    @Test
    public void selectMileTotal() {
        List<MileDTO> dto = mapper.selectMileTotal("test1");
        System.out.println(dto.toString());
    }
}