package service;

import connectDB.Connector;
import model.Category;
import model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        String query = "SELECT * FROM product\n" +
                "JOIN category c on product.category_id = c.category_id;";
        try {
            statement = connection.prepareStatement(query);
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
        String query = "SELECT * FROM product\n" +
                "JOIN category c on product.category_id = c.category_id\n" +
                "WHERE product_id = ?";
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
                category.setId(resultSet.getInt("category_id"));
                category.setName(resultSet.getString("category_name"));
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
        String query = "INSERT INTO product(category_id, product_name, product_price, quantity, color, description) \n" +
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
        String query = "UPDATE product\n" +
                "SET category_id   = ?,\n" +
                "    product_name  = ?,\n" +
                "    product_price = ?,\n" +
                "    quantity      = ?,\n" +
                "    color         = ?,\n" +
                "    description   = ?\n" +
                "WHERE product_id = ?;";
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
        String query = "DELETE FROM product where product_id = ?";
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
        String query = "SELECT * FROM product\n" +
                "                JOIN category c on product.category_id = c.category_id\n" +
                "WHERE product_name LIKE ?;";
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
        product.setProductId(resultSet.getInt("product_id"));
        product.setName(resultSet.getString("product_name"));
        product.setPrice(resultSet.getInt("product_price"));
        product.setQuantity(resultSet.getInt("quantity"));
        product.setColor(resultSet.getString("color"));
        product.setDescription(resultSet.getString("description"));
        category.setId(resultSet.getInt("c.category_id"));
        category.setName(resultSet.getString("category_name"));
        product.setCategory(category);
        return product;
    }
}
