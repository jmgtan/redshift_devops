package org.rsdevops.test;

import org.junit.jupiter.api.Test;

import java.sql.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpGenOrdersTests extends AbstractDataDrivenTest {

    @Test
    public void genOrdersTest() throws SQLException, ClassNotFoundException {
        clearTable("orders");

        String email = getRandomEmail();

        int numOfOrders = 50;
        CallableStatement cs = getOrCreateConnection().prepareCall("{call gen_orders(?, ?)}");
        cs.setString(1, email);
        cs.setInt(2, numOfOrders);
        cs.execute();

        PreparedStatement ps = getOrCreateConnection().prepareStatement("select count(*) from orders where email=?");
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();

        rs.next();

        int count = rs.getInt(1);

        assertEquals(numOfOrders, count, "The number of orders created should be the same as the parameter");
    }

    private String getRandomEmail() throws SQLException, ClassNotFoundException {
        Statement stmt = getOrCreateConnection().createStatement();
        ResultSet rs = stmt.executeQuery("select email from users order by random() limit 1");
        rs.next();

        return rs.getString(1);
    }
}
