package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Country;
import model.Department;
import model.RememberedUser;
import model.StudentForApprove;
import model.Book;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DBtoArrayList {

	static String url ="jdbc:postgresql://localhost:5432/LibraryManagementDB" ;
	static String username = "postgres";
	static String password = "eren20044";
	static Connection con;

	
	public static ArrayList<Country> countryToArrayList() {
		try {
			con = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			System.out.println("An error: " + e);
		}
		String sql = "SELECT * FROM countrycodes";
		ArrayList<Country> countryArrayList = new ArrayList<Country>();
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				model.Country country = new Country(rs.getString(1), rs.getString(2));
				countryArrayList.add(country);
			}
		} catch (SQLException e) {
			System.out.println("An error: " + e);
		}
		return countryArrayList;
	}
	


	public static ArrayList<Department> departmentToArrayList() {
		try {
			con = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			System.out.println("An error: " + e);
		}
		String sql = "SELECT * FROM departments";
		ArrayList<Department> departmentArrayList = new ArrayList<Department>();
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				model.Department department = new Department(rs.getString(1));
				departmentArrayList.add(department);
			}
		} catch (SQLException e) {
			System.out.println("An error: " + e);
		}
		return departmentArrayList;
	}
	
	public static ArrayList<RememberedUser> rememberedToArrayList() {
		try {
			con = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			System.out.println("An error: " + e);
		}
		String sql = "SELECT * FROM remembereduser";
		ArrayList<RememberedUser> userArrayList = new ArrayList<RememberedUser>();
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				model.RememberedUser users = new RememberedUser(rs.getString(1));
				userArrayList.add(users);
			}
		} catch (SQLException e) {
			System.out.println("An error: " + e);
		}
		return userArrayList;
	}
	
	
	public static ArrayList<Book> SearchedBookToArrayList(String sql) {
		try {
			con = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			System.out.println("An error: " + e);
		}
		ArrayList<Book> bookArrayList = new ArrayList<Book>();
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				Book book = new Book(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
				bookArrayList.add(book);
			}
		} catch (SQLException e) {
			System.out.println("An error: " + e);
		}
		return bookArrayList;
	}

	
	public static ArrayList<StudentForApprove> StudentForApproveToArrayList(String sql) {
		try {
			con = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			System.out.println("An error: " + e);
		}
		ArrayList<StudentForApprove> studentArrayList = new ArrayList<StudentForApprove>();
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				StudentForApprove student = new StudentForApprove(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
				studentArrayList.add(student);
			}
		} catch (SQLException e) {
			System.out.println("An error: " + e);
		}
		return studentArrayList;
	}
	
	public static ArrayList<String> categoryToArrayList() {
		try {
			con = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			System.out.println("An error: " + e);
		}
		String sql = "SELECT * FROM bookcategories";
		ArrayList<String> categoryArrayList = new ArrayList<String>();
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				categoryArrayList.add(rs.getString(1));
			}
		} catch (SQLException e) {
			System.out.println("An error: " + e);
		}
		return categoryArrayList;
	}

	
}
