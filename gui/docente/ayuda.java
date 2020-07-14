package gui.docente;

import accesoDatos.CuentaDAOImpl;
import org.junit.Test;

public class ayuda {
	@Test
	public void testayuda (){
		CuentaDAOImpl cuentaDAO = new CuentaDAOImpl();
		Integer result = cuentaDAO.buscarCuentaUsuario("ana@hotmail.com","118a866e3414dc5f4366601bf1b7a7dc3fd0a10fdb8c2573dc4bb6cf1adafd5788e05e8ecb11924d187e065446f2d5c5b311cd84afeb705a46315bf6606caf9b");
		System.out.println(result);

	}
}
