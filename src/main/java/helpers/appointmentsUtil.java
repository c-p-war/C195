package helpers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.ReportMonth;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Utility methods for the Appointments object. Holds SQL statements.
 */
public class appointmentsUtil {
    /**
     * @return All appointments in the DB
     * @throws SQLException
     */
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

    /**
     * @param in_userId
     * @return All appointments for the provided user id
     * @throws SQLException
     */
    public static ObservableList<Appointment> getAppointmentsByUser(int in_userId) throws SQLException {
        String getFifteen = "SELECT * FROM appointments where User_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareCall((getFifteen));
        ps.setInt(1, in_userId);
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

    /**
     * @return All appointments with a start date during the current week
     * @throws SQLException
     */
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

    /**
     * @return All appointments with a start date during the current month
     * @throws SQLException
     */
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

    public static ObservableList<ReportMonth> reportMonths() throws SQLException {
        String sql = "SELECT MONTHNAME(Start) as month, type, count(*) AS count FROM appointments GROUP BY Type, MONTHNAME(Start)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        ObservableList<ReportMonth> reportList = FXCollections.observableArrayList();
        while (rs.next()) {
            String type = rs.getString("type");
            String month = rs.getString("month");
            int count = rs.getInt("count");
            ReportMonth report = new ReportMonth(type, month, count);
            reportList.add(report);
        }
        return reportList;
    }

    /**
     * Adds an appointment to the appointments table
     *
     * @param appointment
     */
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

    /**
     * Updates a provided appointments record in the DB
     *
     * @param appointment
     */
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

    /**
     * Delete the provided appointment from the DB
     *
     * @param appointment
     * @throws SQLException
     */
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
