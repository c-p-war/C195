package helpers;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.ReportMonth;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class appointmentsUtil {
    public static ObservableList<Appointment> getAppointments() throws SQLException {
        String getAppointments = "SELECT * FROM appointments";
        PreparedStatement ps = JDBC.connection.prepareCall((getAppointments));
        ResultSet rs = ps.executeQuery();
        ObservableList<Appointment> appointmentsList = FXCollections.observableArrayList();

        while (rs.next()) {
            int id = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
            int customerId = rs.getInt("Customer_ID");
            int userId = rs.getInt("User_ID");
            String contactName = contactUtil.getContactName(rs.getInt("Contact_ID"));
            Appointment appointment = new Appointment(id, title, description, location, type, start, end, customerId, userId, contactName);
            appointmentsList.add(appointment);
        }
        return appointmentsList;
    }

    public static ObservableList<Appointment> getFifteen() throws SQLException {
        String getFifteen = "SELECT * FROM appointments where START >= CURRENT_TIMESTAMP AND START <= DATE_ADD(CURRENT_TIMESTAMP, INTERVAL 15 MINUTE)";
        PreparedStatement ps = JDBC.connection.prepareCall((getFifteen));
        ResultSet rs = ps.executeQuery();
        ObservableList<Appointment> fifteenList = FXCollections.observableArrayList();

        while (rs.next()) {
            int id = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
            int customerId = rs.getInt("Customer_ID");
            int userId = rs.getInt("User_ID");
            String contactName = contactUtil.getContactName(rs.getInt("Contact_ID"));
            Appointment appointment = new Appointment(id, title, description, location, type, start, end, customerId, userId, contactName);
            fifteenList.add(appointment);
        }
        System.out.println(fifteenList);
        return fifteenList;
    }

    public static ObservableList<Appointment> getWeek() throws SQLException {
        String getWeek = "SELECT * FROM appointments where WEEK(START) = WEEK(CURRENT_TIMESTAMP)";
        PreparedStatement ps = JDBC.connection.prepareCall((getWeek));
        ResultSet rs = ps.executeQuery();
        ObservableList<Appointment> weekList = FXCollections.observableArrayList();

        while (rs.next()) {
            int id = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
            int customerId = rs.getInt("Customer_ID");
            int userId = rs.getInt("User_ID");
            String contactName = contactUtil.getContactName(rs.getInt("Contact_ID"));
            Appointment appointment = new Appointment(id, title, description, location, type, start, end, customerId, userId, contactName);
            weekList.add(appointment);
        }
        return weekList;
    }

    public static ObservableList<Appointment> getMonth() throws SQLException {
        String getMonth = "SELECT * FROM appointments WHERE MONTH(START) = MONTH(CURRENT_TIMESTAMP)";
        PreparedStatement ps = JDBC.connection.prepareCall((getMonth));
        ResultSet rs = ps.executeQuery();
        ObservableList<Appointment> monthList = FXCollections.observableArrayList();

        while (rs.next()) {
            int id = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
            int customerId = rs.getInt("Customer_ID");
            int userId = rs.getInt("User_ID");
            String contactName = contactUtil.getContactName(rs.getInt("Contact_ID"));
            Appointment appointment = new Appointment(id, title, description, location, type, start, end, customerId, userId, contactName);
            monthList.add(appointment);
        }
        return monthList;
    }

    // TODO: Check date conversions
    public static void updateStart(Appointment appointment) throws SQLException {
        // TODO: Date comparison and alert
        // TODO: Appointment overlap comparison and alert
        String updateStart = "UPDATE appointments SET Start = ? WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(updateStart);
        ps.setTimestamp(1, Timestamp.valueOf(appointment.getStart()));
        ps.setInt(2, appointment.getId());
        ps.executeUpdate();
    }

    // TODO: Check date conversions
    public static void updateEnd(Appointment appointment) throws SQLException {
        // TODO: Date comparison and alert
        // TODO: Appointment overlap comparison and alert
        String updateEnd = "UPDATE appointments SET End = ? WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(updateEnd);
        ps.setTimestamp(1, Timestamp.valueOf(appointment.getStart()));
        ps.setInt(2, appointment.getId());
        ps.executeUpdate();
    }

    public static ArrayList<String> getDistinctTypes() throws SQLException {
        String sql = "SELECT DISTINCT Type FROM appointments";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        ArrayList<String> distinctTypes = new ArrayList();
        while (rs.next()) {
            String type = rs.getString("Type");
            distinctTypes.add(type);
        }
        // TODO: Lambda?
        System.out.println(distinctTypes);
        return distinctTypes;
    }

    public static int countTypes(String in_type) throws SQLException {
        String sql = "SELECT COUNT(*) AS ? FROM appointments WHERE Type = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, "count");
        ps.setString(2, in_type);
        ps.executeQuery();
        ResultSet rs = ps.executeQuery();
        ArrayList<Integer> distinctCount = new ArrayList();
        while (rs.next()) {
            int count = rs.getInt("count");
            distinctCount.add(count);
        }
        return distinctCount.get(0);
    }

    public static ObservableList<ReportMonth> reportMonths() throws SQLException {
        String sql = "SELECT MONTH(Start) AS ?,COUNT(*) AS ? FROM appointments GROUP BY MONTH(Start)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, "month");
        ps.setString(2, "count");
        ResultSet rs = ps.executeQuery();
        ObservableList<ReportMonth> reportList = FXCollections.observableArrayList();
        while (rs.next()) {
            int monthInt = rs.getInt("month");
            int count = rs.getInt("count");
            String monthString = null;
            // TODO: Add other months
            switch (monthInt) {
                case 1:
                    monthString = "January";
                    break;
                case 2:
                    monthString = "February";
                    break;
                case 5:
                    monthString = "May";
                    break;
                case 10:
                    monthString = "October";
                    break;
            }
            ReportMonth report = new ReportMonth(monthInt, monthString, count);
            reportList.add(report);
        }
        System.out.println(reportList.get(0).getMonthString());
        return reportList;
    }

    public static void addAppointment(Appointment appointment) {
        // TODO: Date comparison and alert
        // TODO: Appointment overlap comparison and alert
        try {
            String sql = "INSERT INTO appointments (Appointment_ID, Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, appointment.getId());
            ps.setString(2, appointment.getTitle());
            ps.setString(3, appointment.getDescription());
            ps.setString(4, appointment.getLocation());
            ps.setString(5, appointment.getType());
            ps.setTimestamp(6, Timestamp.valueOf(appointment.getStart()));
            ps.setTimestamp(7, Timestamp.valueOf(appointment.getEnd()));
            ps.setInt(8, appointment.getCustomerId());
            ps.setInt(9, appointment.getUserId());
            ps.setInt(10, contactUtil.getContactId(appointment.getContactName()));
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void updateAppointment(Appointment appointment) {
        // TODO: Date comparison and alert
        // TODO: Appointment overlap comparison and alert
        try {
            String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, appointment.getTitle());
            ps.setString(2, appointment.getDescription());
            ps.setString(3, appointment.getLocation());
            ps.setString(4, appointment.getType());
            ps.setTimestamp(5, Timestamp.valueOf(appointment.getStart()));
            ps.setTimestamp(6, Timestamp.valueOf(appointment.getEnd()));
            ps.setInt(7, appointment.getCustomerId());
            ps.setInt(8, appointment.getUserId());
            ps.setInt(10, contactUtil.getContactId(appointment.getContactName()));
            ps.setInt(10, appointment.getId());
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void deleteAppointment(Appointment appointment) throws SQLException {
        try {
            String deleteAppointment = "DELETE FROM appointments WHERE Appointment_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareCall(deleteAppointment);
            ps.setInt(1, appointment.getId());
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


}
