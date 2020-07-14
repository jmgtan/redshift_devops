package org.jantan.test;

import org.junit.jupiter.api.Test;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

public class SpMergeStagedProductsTests extends AbstractDataDrivenTest {

    @Test
    public void mergeTest() throws SQLException, ClassNotFoundException {
        CallableStatement cs = getOrCreateConnection().prepareCall("{call merge_staged_products()}");
        cs.execute();

        PreparedStatement ps = getOrCreateConnection().prepareStatement("select count(*) from products where status=?");
        ps.setString(1, "CLOSED");
        ResultSet rs = ps.executeQuery();

        rs.next();

        assertEquals(EXPECTED_PRODUCTS_STAGING, rs.getInt(1), "Expected that the number of closed rows would be the same as the number of duplicate entries in staging."); //test the expected number of rows that have been "closed"

        PreparedStatement ps2 = getOrCreateConnection().prepareStatement("select close_date from products where status=? limit 1");
        ps2.setString(1, "CLOSED");
        ResultSet rs2 = ps2.executeQuery();

        rs2.next();

        Date date = rs2.getDate(1);

        assertNotNull(date, "Expected to have a close date when a new entry is created for the same dimension.");

        PreparedStatement ps3 = getOrCreateConnection().prepareStatement("select count(*) from products_staging");
        ResultSet rs3 = ps3.executeQuery();

        rs3.next();

        assertEquals(0, rs3.getInt(1), "Expected that the staging would be cleared after the data is merged.");
    }
}
