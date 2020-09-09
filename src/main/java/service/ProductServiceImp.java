package service;

import connectDB.Connector;
import model.Category;
import model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ProductServiceImp implements IProductService {
    private Connection connection;
    private PreparedStatement statement;

    public ProductServiceImp() {
        connection = Connector.getConnection();
    }

    @Override
    public List<Product> getAllProduct() {
        List<Product> productList = new LinkedList<>();
        String sql = "SELECT * FROM products";
//                "JOIN category c on products.categoryId = c.categoryId;";
        try {
            statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Product product = parseResultSet(resultSet);
                productList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    @Override
    public Product getProductById(int id) {
        Product product = null;
        String query = "SELECT * FROM products"+
                "JOIN category c on products.categoryId = c.categoryId" +
                "WHERE productId = ?";
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.first();
            product = parseResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    @Override
    public List<Category> getCategoryList() {
        String query = "SELECT * FROM category";
        List<Category> categoryList = new LinkedList<>();
        try {
            statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Category category = new Category();
                category.setId(resultSet.getInt("categoryId"));
                category.setName(resultSet.getString("categoryName"));
                categoryList.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categoryList;
    }

    @Override
    public boolean addProduct(Product product) {
        int rowsAffect = 0;
        String query = "INSERT INTO products(productId, productName, productPrice, productQuantity, productColor, productDes)" +
                "VALUES (?,?,?,?,?,?);";
        try {
            statement = connection.prepareStatement(query);
            setProduct(product);
            rowsAffect = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffect > -1;
    }

    @Override
    public boolean editProduct(int id, Product product) {
        int rowsAffect = 0;
        String query = "UPDATE product" +
                "SET categoryId   = ?," +
                "    productName  = ?," +
                "    productPrice = ?," +
                "    productQuantity      = ?," +
                "    productColor         = ?," +
                "    producDes   = ?" +
                "WHERE productId = ?;";
        try {
            statement = connection.prepareStatement(query);
            setProduct(product);
            statement.setInt(7, id);
            rowsAffect = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffect > -1;
    }

    @Override
    public boolean deleteProduct(int id) {
        int rowsAffect = 0;
        String query = "DELETE FROM products where productId = ?";
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            rowsAffect = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffect > -1;
    }

    @Override
    public List<Product> searchProductByName(String name) {
        List<Product> productList = new LinkedList<>();
        String query = "SELECT * FROM products" +
                "                JOIN category c on products.categoryId = c.categoryId" +
                "WHERE productName LIKE ?;";
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, name + "%");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                productList.add(parseResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    private void setProduct(Product product) throws SQLException {
        statement.setInt(1, product.getCategory().getId());
        statement.setString(2, product.getName());
        statement.setInt(3, product.getPrice());
        statement.setInt(4, product.getQuantity());
        statement.setString(5, product.getColor());
        statement.setString(6, product.getDescription());
    }

    private Product parseResultSet(ResultSet resultSet) throws SQLException {
        Product product = new Product();
        Category category = new Category();
        product.setProductId(resultSet.getInt("productId"));
        product.setName(resultSet.getString("productName"));
        product.setPrice(resultSet.getInt("productPrice"));
        product.setQuantity(resultSet.getInt("productQuantity"));
        product.setColor(resultSet.getString("productColor"));
        product.setDescription(resultSet.getString("productDes"));
        category.setId(resultSet.getInt("products.categoryId"));
        category.setId(resultSet.getInt("c.categoryId"));
        category.setName(resultSet.getString("categoryName"));
        product.setCategory(category);
        return product;
    }
}
