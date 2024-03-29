
package Controle;

import Modelo.Produto;
import ModeloDao.ProdutoDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "ControleProduto", urlPatterns = {"/ControleProduto"})
public class ControleProduto extends HttpServlet {

   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            String acao = request.getParameter("acao");
            HttpSession sessao = request.getSession();   
            if (acao.equals("listaProdutos")) {
                //monta uma lista para exibição na pagina principal
                ArrayList<Produto> produtos = new ProdutoDao().listar();
                //armazena os produto na requisição
                sessao.setAttribute("produtos", produtos);
                //envia para index.jsp
                response.sendRedirect("index.jsp");
            }
            } catch (Exception erro) {
            request.setAttribute("erro", erro);
            request.getRequestDispatcher("erro.jsp").forward(request, response);
            
        }
    }

   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    
}
