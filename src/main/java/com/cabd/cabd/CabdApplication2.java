package com.cabd.cabd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CabdApplication2 {
	public static void main(String[] args) {
		// Configurația conexiunii la baza de date
		String url = "jdbc:oracle:thin:@193.231.20.20:15211:orcl19c";
		String username = "pmmbd1r39";
		String password = "pmmbd1r39";

		try (Connection connection = DriverManager.getConnection(url, username, password)) {
			System.out.println("Conexiunea la baza de date a fost realizată cu succes!");

			displayProducts(connection);

			displayOrders(connection);

			displayProductHistory(connection);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void displayProducts(Connection connection) {
		System.out.println("Produse în tabelul Products:");
		String query = "SELECT Product_ID, Name, Price, Stock_Quantity, Valid_From, Valid_To FROM Products";
		try (PreparedStatement statement = connection.prepareStatement(query);
			 ResultSet resultSet = statement.executeQuery()) {

			if (!resultSet.isBeforeFirst()) {
				System.out.println("Tabelul Products este gol.");
			} else {
				while (resultSet.next()) {
					long productId = resultSet.getLong("Product_ID");
					String name = resultSet.getString("Name");
					double price = resultSet.getDouble("Price");
					int stockQuantity = resultSet.getInt("Stock_Quantity");
					String validFrom = resultSet.getTimestamp("Valid_From").toString();
					String validTo = resultSet.getTimestamp("Valid_To").toString();

					System.out.println("Product ID: " + productId);
					System.out.println("Name: " + name);
					System.out.println("Price: " + price);
					System.out.println("Stock Quantity: " + stockQuantity);
					System.out.println("Valid From: " + validFrom);
					System.out.println("Valid To: " + validTo);
					System.out.println("--------------------------");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void displayOrders(Connection connection) {
		System.out.println("Comenzi în tabelul Orders:");
		String query = "SELECT Order_ID, Product_ID, Username, Quantity FROM Orders";
		try (PreparedStatement statement = connection.prepareStatement(query);
			 ResultSet resultSet = statement.executeQuery()) {

			if (!resultSet.isBeforeFirst()) {
				System.out.println("Tabelul Orders este gol.");
			} else {
				while (resultSet.next()) {
					long orderId = resultSet.getLong("Order_ID");
					long productId = resultSet.getLong("Product_ID");
					String username = resultSet.getString("Username");
					int quantity = resultSet.getInt("Quantity");

					System.out.println("Order ID: " + orderId);
					System.out.println("Product ID: " + productId);
					System.out.println("Username: " + username);
					System.out.println("Quantity: " + quantity);
					System.out.println("--------------------------");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void displayProductHistory(Connection connection) {
		System.out.println("Istoric în tabelul Product_History:");
		String query = "SELECT History_ID, Product_ID, Product_Name, Price, Stock_Quantity, Valid_From, Valid_To, Change_Type, Hist_Date FROM Product_History";
		try (PreparedStatement statement = connection.prepareStatement(query);
			 ResultSet resultSet = statement.executeQuery()) {

			if (!resultSet.isBeforeFirst()) {
				System.out.println("Tabelul Product_History este gol.");
			} else {
				while (resultSet.next()) {
					long historyId = resultSet.getLong("History_ID");
					long productId = resultSet.getLong("Product_ID");
					String productName = resultSet.getString("Product_Name");
					double price = resultSet.getDouble("Price");
					int stockQuantity = resultSet.getInt("Stock_Quantity");
					String validFrom = resultSet.getTimestamp("Valid_From").toString();
					String validTo = resultSet.getTimestamp("Valid_To").toString();
					String changeType = resultSet.getString("Change_Type");
					String histDate = resultSet.getTimestamp("Hist_Date").toString();

					System.out.println("History ID: " + historyId);
					System.out.println("Product ID: " + productId);
					System.out.println("Product Name: " + productName);
					System.out.println("Price: " + price);
					System.out.println("Stock Quantity: " + stockQuantity);
					System.out.println("Valid From: " + validFrom);
					System.out.println("Valid To: " + validTo);
					System.out.println("Change Type: " + changeType);
					System.out.println("Hist Date: " + histDate);
					System.out.println("--------------------------");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
