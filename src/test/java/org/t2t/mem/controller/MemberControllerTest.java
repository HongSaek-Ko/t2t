package org.t2t.mem.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberControllerTest {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/t2t?serverTimezone=UTC&characterEncoding=UTF-8", "test1", "1234");
        con.setAutoCommit(false);
        PreparedStatement pstmt = con.prepareStatement("INSERT INTO CMP" +
                "(CMPID, USRID, PRDID, CMPCATECD, CMPSTAT, ADMID, CRSTCD)" +
                "VALUES(?, ?, ?, ?, ?, ?, ?)");

        for(int i = 2; i < 30; i++){
            pstmt.setInt(1, i); //신고순번
            pstmt.setString(2, "test" + i); //사용자
            pstmt.setInt(3,  i); //상품번호
            pstmt.setString(4, "CCM03"); //신고유형번호
            pstmt.setString(5, "CMP01"); //신고상태 (1:신고중, 2:신고처리완료 3:신고반려)
            pstmt.setString(6, "ADMINTEST"); //관리자 Id
            pstmt.setString(7, "CRT01"); //신고처리결과

            pstmt.addBatch();
            pstmt.clearParameters();
        }

        pstmt.executeBatch();
        con.commit();
        pstmt.clearBatch();
        pstmt.close();
        con.close();


    }
}