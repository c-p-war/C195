package helpers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class appointmentsUtil {
    public static ObservableList<Appointment> getAppointments()throws SQLException{
        String getAppointments = "SELECT * FROM appointments";
        PreparedStatement ps = JDBC.connection.prepareCall((getAppointments));
        ResultSet rs = ps.executeQuery();
        ObservableList<Appointment> appointmentsList = FXCollections.observableArrayList();

        while (rs.next()){
            int id = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
            int customerId = rs.getInt("Customer_ID");
            int userId = rs.getInt("User_ID");
            int contactId = rs.getInt("Contact_ID");
            Appointment appointment = new Appointment(id, title, description, location, type, start, end, customerId, userId, contactId);
            appointmentsList.add(appointment);
        }
        System.out.println(appointmentsList);
        return appointmentsList;
    }

    public static void addAppointment(Appointment appointment){
        try {
            String sql = "INSERT INTO appointments (Appointment_ID, Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, appointment.getId());
            ps.setString(2,appointment.getTitle());
            ps.setString(3, appointment.getDescription());
            ps.setString(4, appointment.getLocation());
            ps.setString(5,appointment.getType());
            ps.setTimestamp(6, Timestamp.valueOf(appointment.getStart()));
            ps.setTimestamp(7, Timestamp.valueOf(appointment.getEnd()));
            ps.setInt(8,appointment.getCustomerId());
            ps.setInt(9,appointment.getUserId());
            ps.setInt(10,appointment.getContactId());
            ps.executeUpdate();
        } catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }

    public static void updateAppointment(Appointment appointment){
        try {
            String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1,appointment.getTitle());
            ps.setString(2, appointment.getDescription());
            ps.setString(3, appointment.getLocation());
            ps.setString(4,appointment.getType());
            ps.setTimestamp(5, Timestamp.valueOf(appointment.getStart()));
            ps.setTimestamp(6, Timestamp.valueOf(appointment.getEnd()));
            ps.setInt(7,appointment.getCustomerId());
            ps.setInt(8,appointment.getUserId());
            ps.setInt(9,appointment.getContactId());
            ps.setInt(10, appointment.getId());
            ps.executeUpdate();
        } catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }

    public static void deleteAppointment(Appointment appointment) throws SQLException {
        try {
            String deleteAppointment = "DELETE FROM appointments WHERE Appointment_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareCall(deleteAppointment);
            ps.setInt(1,appointment.getId());
            ps.executeUpdate();
        } catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }



}
