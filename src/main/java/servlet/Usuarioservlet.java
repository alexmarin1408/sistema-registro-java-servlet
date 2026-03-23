package servlet;

import conexion.Conexion; // Importa tu clase de conexión
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@WebServlet("/GuardarUsuario")
public class Usuarioservlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 1. Recibir datos del formulario (index.html)
        String nombre = request.getParameter("nombre");
        String correo = request.getParameter("correo");

        // 2. Guardar en la base de datos (XAMPP)
        String sql = "INSERT INTO usuarios (nombre, correo) VALUES (?, ?)";
        
        try (Connection con = Conexion.conectar(); 
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, nombre);
            ps.setString(2, correo);
            ps.executeUpdate(); // Ejecuta la inserción
            
            System.out.println("¡Datos guardados con éxito en la BD!");
            
        } catch (Exception e) {
            System.out.println("Error al guardar: " + e.getMessage());
        }

        // 3. Redirigir al JSP de resultado
        request.setAttribute("nombreAtributo", nombre);
        request.setAttribute("correoAtributo", correo);
       // request.getRequestDispatcher("resultado.jsp").forward(request, response);
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<html><body>");
            out.println("<h1>Datos Recibidos</h1>");
            out.println("<p>Nombre: " + nombre + "</p>");
            out.println("<p>Correo: " + correo + "</p>");
            out.println("<br><a href='index.html'>Volver</a>");
            out.println("</body></html>");
 }   
}
    }