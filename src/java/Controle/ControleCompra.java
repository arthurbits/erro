
package Controle;

import Bll.BllCompra;
import Modelo.CarrinhoDeCompra;
import Modelo.Cliente;
import Modelo.Compra;
import Modelo.Endereco;
import Modelo.Usuario;
import java.io.IOException;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet(name = "ControleCompra", urlPatterns = {"/ControleCompra"})
public class ControleCompra extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");        
    }

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String acao = request.getParameter("acao");             
            HttpSession sessao = request.getSession(); 
            Usuario usuario = (Usuario)sessao.getAttribute("usuario");
            Cliente cliente = (Cliente) sessao.getAttribute("cliente");
            Endereco endereco = (Endereco) sessao.getAttribute("endereco");
            CarrinhoDeCompra carrinho = (CarrinhoDeCompra) sessao.getAttribute("carrinho");
            BllCompra bll = new BllCompra();
             if(acao.equals("Finalizar Pedido")){          
                Compra compra = new Compra(cliente,carrinho);
                compra.setDataCompra(new Date());
                compra.setValor(carrinho.getTotal());
                compra.setFormaDePagamento(request.getParameter("forma")); 
                bll.cadastroCompra(compra);                
                sessao.removeAttribute("carrinho"); 
                sessao.removeAttribute("msgCheck");
                String msg = "Compra feita com sucesso";                
                sessao.setAttribute("msg", msg);
                response.sendRedirect("index.jsp");           
                 
             }
            
            } catch (Exception erro) {
            request.setAttribute("erro", erro);
            request.getRequestDispatcher("/erro.jsp").forward(request, response);
        }
    }

    

}
