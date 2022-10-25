package helpers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.ReportMonth;
import model.ReportType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

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
            String start = util.utcToLocal(rs.getTimestamp("Start").toLocalDateTime());
            String end = util.utcToLocal(rs.getTimestamp("End").toLocalDateTime());
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
            String start = util.utcToLocal(rs.getTimestamp("Start").toLocalDateTime());
            String end = util.utcToLocal(rs.getTimestamp("End").toLocalDateTime());
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
            String start = util.utcToLocal(rs.getTimestamp("Start").toLocalDateTime());
            String end = util.utcToLocal(rs.getTimestamp("End").toLocalDateTime());
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
            String start = util.utcToLocal(rs.getTimestamp("Start").toLocalDateTime());
            String end = util.utcToLocal(rs.getTimestamp("End").toLocalDateTime());
            int customerId = rs.getInt("Customer_ID");
            int userId = rs.getInt("User_ID");
            String contactName = contactUtil.getContactName(rs.getInt("Contact_ID"));
            Appointment appointment = new Appointment(id, title, description, location, type, start, end, customerId, userId, contactName);
            monthList.add(appointment);
        }
        return monthList;
    }

    public static ObservableList<ReportType> getDistinctTypes() throws SQLException {
        String sql = "SELECT DISTINCT Type, count(Type) as ? FROM appointments GROUP BY Type";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, "count");
        ResultSet rs = ps.executeQuery();
        ObservableList<ReportType> distinctTypes = FXCollections.observableArrayList();
        while (rs.next()) {
            String type = rs.getString("Type");
            int count = rs.getInt("count");
            ReportType reportType = new ReportType(type, count);
            distinctTypes.add(reportType);
        }
        return distinctTypes;
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
            switch (monthInt) {
                case 1:
                    monthString = "January";
                    break;
                case 2:
                    monthString = "February";
                    break;
                case 3:
                    monthString = "March";
                    break;
                case 4:
                    monthString = "April";
                    break;
                case 5:
                    monthString = "May";
                    break;
                case 6:
                    monthString = "June";
                    break;
                case 7:
                    monthString = "July";
                    break;
                case 8:
                    monthString = "August";
                    break;
                case 9:
                    monthString = "September";
                    break;
                case 10:
                    monthString = "October";
                    break;
                case 11:
                    monthString = "November";
                    break;
                case 12:
                    monthString = "December";
                    break;
            }
            ReportMonth report = new ReportMonth(monthInt, monthString, count);
            reportList.add(report);
        }
        System.out.println(reportList.get(0).getMonthString());
        return reportList;
    }

    public static void addAppointment(Appointment appointment) {
        try {
            String sql = "INSERT INTO appointments (Appointment_ID, Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, appointment.getId());
            ps.setString(2, appointment.getTitle());
            ps.setString(3, appointment.getDescription());
            ps.setString(4, appointment.getLocation());
            ps.setString(5, appointment.getType());
            ps.setString(6, appointment.getStart());
            ps.setString(7, appointment.getEnd());
            ps.setInt(8, appointment.getCustomerId());
            ps.setInt(9, appointment.getUserId());
            ps.setInt(10, contactUtil.getContactId(appointment.getContactName()));
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void updateAppointment(Appointment appointment) {
        try {
            String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, appointment.getTitle());
            ps.setString(2, appointment.getDescription());
            ps.setString(3, appointment.getLocation());
            ps.setString(4, appointment.getType());
            ps.setString(5, appointment.getStart());
            ps.setString(6, appointment.getEnd());
            ps.setInt(7, appointment.getCustomerId());
            ps.setInt(8, appointment.getUserId());
            ps.setInt(9, contactUtil.getContactId(appointment.getContactName()));
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
