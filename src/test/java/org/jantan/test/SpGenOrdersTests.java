package org.jantan.test;

import org.junit.jupiter.api.Test;

import java.sql.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpGenOrdersTests extends AbstractDataDrivenTest {

    @Test
    public void succeedTest() throws SQLException {
        clearTable("orders");

        String email = getRandomEmail();

        int numOfOrders = 50;
        CallableStatement cs = conn.prepareCall("{call gen_orders(?, ?)}");
        cs.setString(1, email);
        cs.setInt(2, numOfOrders);
        cs.execute();

        PreparedStatement ps = conn.prepareStatement("select count(*) from orders where email=?");
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();

        rs.next();

        int count = rs.getInt(1);

        assertEquals(numOfOrders, count);
    }

    private String getRandomEmail() throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("select email from users order by random() limit 1");
        rs.next();

        return rs.getString(1);
    }
}
