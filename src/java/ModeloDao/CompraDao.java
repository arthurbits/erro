
package ModeloDao;

import Modelo.CarrinhoDeCompra;
import Modelo.Cliente;
import Modelo.Compra;
import Util.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class CompraDao {
    private static final String CADASTRAR = "insert into compra (dataCompra, valor,formadepagamento,idclientefk) values (?,?,?,?) ";
    private static final String BUSCAR = "select * from compra where idclientefk = ? and dataCompra = ? ";
    
    public void cadastrar (Compra compra ){
       Connection conexao = null;
       try{
            conexao = Conexao.getConexao();           
            PreparedStatement pstmt = conexao.prepareStatement(CADASTRAR);             
            java.sql.Timestamp dataSql = new java.sql.Timestamp(compra.getDataCompra().getTime());            
            pstmt.setTimestamp(1, dataSql);
            pstmt.setDouble(2, compra.getValor() );
            pstmt.setString(3, compra.getFormaDePagamento());           
            pstmt.setInt(4,compra.getCliente().getIdCliente());           
            pstmt.execute();         
           
       }catch (Exception e ){
           
           throw new RuntimeException(e);
           
       }finally{
           
           try{conexao.close();
           
           }catch (SQLException ex){
               
               throw new RuntimeException (ex);
           }
        }
   }//FIM CADASTRAR
    
    public Compra buscarCompra (Cliente cliente, Date data, CarrinhoDeCompra carrinho)throws SQLException, ClassNotFoundException {
        Connection conexao = null;
        Compra compraL = new Compra();      
        try{            
            conexao = Conexao.getConexao();
            PreparedStatement pstmt = conexao.prepareStatement(BUSCAR);
            java.sql.Timestamp dataSql = new java.sql.Timestamp(data.getTime());    
            pstmt.setInt(1, cliente.getIdCliente());
            pstmt.setTimestamp(2, dataSql);
            ResultSet rs = pstmt.executeQuery();
        while (rs.next()){        //a cada loop                     
            compraL.setIdCompra(rs.getInt("idCompra"));
            compraL.setDataCompra(rs.getDate("dataCompra"));
            compraL.setValor(rs.getDouble("valor"));
            compraL.setFormaDePagamento(rs.getString("formaDePagamento"));
            compraL.setCliente(cliente);      
            compraL.setCarrinho(carrinho);
        }
            return compraL;            
        }catch(Exception e ){
            throw new RuntimeException(e);
        }finally{
            try{
                conexao.close();
            }catch (SQLException ex){
                    throw new RuntimeException(ex);
            
                }            
            } 
    }
    
    
}
