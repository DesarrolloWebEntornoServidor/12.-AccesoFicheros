package es.dwes.servlets;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "AccesoAFicheros", urlPatterns = { "/AccesoAFicheros" })
public class AccesoAFicheros extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void procesaSolicitud(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");

		// Deckaramos las variables a null

		try (PrintWriter out = response.getWriter()) {

			imprimirPagina(request, out);
		} catch (Exception e) {

			PrintWriter out = response.getWriter();
			out.println(e);

		}
	}

	private void imprimirPagina(HttpServletRequest request, PrintWriter out) {

		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Servlet AccesoAFicheros</title>");
		out.println("<style>table,thead, tr, td{border:1px solid}</style>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h1>Servlet AccesoAFicheros at "
				+ request.getContextPath() + "</h1>");

		// Visualizamos la ruta y nombre del fichero
		String rutaFichero = request.getServletContext().getRealPath("/")
				+ "\\ficheros\\";
		out.println("rutaFichero=" + rutaFichero + "<br/><br/>");
		String nombreFichero = "Empleados.csv";
		out.println("nombreFichero=" + nombreFichero + "<br/><br/>");

		// Apertura de fichero
		leerFichero(out, rutaFichero, nombreFichero);

		out.println("</body>");
		out.println("</html>");

	}

	private void leerFichero(PrintWriter out, String rutaFichero,
			String nombreFichero) {
		File f;
		BufferedReader br;
		f = new File(rutaFichero + nombreFichero);

		if (f.exists()) {
			out.println("El fichero Existe: <br/><br/>");
			// Creación del FileReader
			try (FileReader fr = new FileReader(f)) {
				br = new BufferedReader(fr);

				// Cabecera de la tabla
				out.println("<table>");
				out.println("<thead><th>Empno</th><th>Nombre</th><th>Cargo</th>"
						+ "<th>Jefe</th><th>FechaNac</th><th>Salario</th><th>Comisión</th><th>Depno</th></thead>");

				// Lectura y visualización del fichero
				String linea;
				String[] empleado;
				while ((linea = br.readLine()) != null) {
					empleado = linea.split(",");
					out.println("<tr>");
					for (int i = 0; i < empleado.length; i++) {
						out.println("<td>" + empleado[i] + "</td>");
					}
					out.println("</tr>");
				}
				out.println("</table>");
			} catch (FileNotFoundException e) {
				out.println("El fichero NO existe");
			} catch (IOException e) {
				out.println("Error al leer el archivo");
			}
		} else
			out.println("El fichero NO existe");
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		procesaSolicitud(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		procesaSolicitud(request, response);
	}

}
