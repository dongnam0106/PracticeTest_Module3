package controller;

import model.Category;
import model.Product;
import service.IProductService;
import service.ProductServiceImp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "DemoServlet", urlPatterns = "/home")
public class ProductServlet extends HttpServlet {
    private IProductService service = new ProductServiceImp();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Product product = null;
        String action = request.getParameter("action");
        List<Product> productList = service.getAllProduct();

        if (action == null) {
            action = "";
        }

        switch (action) {
            case "create":
                product = parseProduct(request);
                boolean result = service.addProduct(product);
                System.out.println(result);
                showProductList(request, response, productList);
                break;
            case "edit":
                product = parseProduct(request);
                result = service.editProduct(product.getProductId(), product);
                System.out.println(result);
                showProductList(request, response, productList);
                break;
            case "search":
                String input = request.getParameter("search-input");
                productList = service.searchProductByName(input);
                showProductList(request, response, productList);
                break;
            default:
                showProductList(request, response, productList);
        }
    }

    private void showProductList(HttpServletRequest request, HttpServletResponse response, List<Product> productList) {

        request.setAttribute("productList", productList);
        try {
            request.getRequestDispatcher("views/listProduct.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    private Product parseProduct(HttpServletRequest request) {
        Product product;
        product = new Product();
        request.setAttribute("productList",product);
        product.setProductId(Integer.parseInt(request.getParameter("productId")));
        product.setName(request.getParameter("productName"));
        product.setPrice(Integer.parseInt(request.getParameter("productPrice")));
        product.setQuantity(Integer.parseInt(request.getParameter("productQuantity")));
        product.setColor(request.getParameter("productColor"));
        product.setDescription(request.getParameter("productDes"));
        Category category = new Category();
        category.setId(Integer.parseInt(request.getParameter("categoryId")));
        product.setCategory(category);
        return product;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Product product = null;
        String action = request.getParameter("action");

        if (action == null) {
            action = "";
        }

        switch (action) {
            case "create":
                product = new Product();
                createProduct(request, response, product, action);
                break;
            case "edit":
                int productId = Integer.parseInt(request.getParameter("id"));
                product = service.getProductById(productId);
                editProduct(request, response, product, action);
                break;
            case "delete":
                productId = Integer.parseInt(request.getParameter("id"));
                System.out.println(service.deleteProduct(productId));
                List<Product> productList = service.getAllProduct();
                showProductList(request, response, productList);
                break;
            default:
                productList = service.getAllProduct();
                showProductList(request, response, productList);
        }
    }

    private void createProduct(HttpServletRequest request, HttpServletResponse response, Product product, String action) {
        List<Category> categoryList = service.getCategoryList();
        request.setAttribute("categoryList", categoryList);
        request.setAttribute("product", product);
        request.setAttribute("action", action);
        try {
            request.getRequestDispatcher("views/createProduct.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    private void editProduct(HttpServletRequest request, HttpServletResponse response, Product product, String action) {
        List<Category> categoryList = service.getCategoryList();
        request.setAttribute("categoryList", categoryList);
        request.setAttribute("product", product);
        request.setAttribute("action", action);
        try {
            request.getRequestDispatcher("views/editProduct.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }
}
