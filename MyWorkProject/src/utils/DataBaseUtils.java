package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *  @author Bruno Konzen Stahl
 *  @since 07/01/2012
 */
public class DataBaseUtils {

	public static String mysqlDriver="org.gjt.mm.mysql.Driver";
	public static String postgreDriver="postgresql.Driver";

	private static Connection con;

	public static String dataBaseConnection = "10.200.2.11";
	public static String dataBaseName = "redesocial";
	public static String dataBaseUser = "ip113";
	public static String dataBasePassword = "alunosenai";

	/**
	 *  Abre conex�o com banco de dados
	 */
	private static void openConnection(){
		try {
			Class.forName(mysqlDriver).newInstance();
			con = DriverManager.getConnection("jdbc:mysql://"+dataBaseConnection+"/"+dataBaseName, dataBaseUser, dataBasePassword);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Controlador de conex�o com banco de dados
	 */
	public static Connection getConnection(){

		//Verificando se existe conex�o
		if(con==null){
			openConnection();
		} 
		//Verificando se a conex�o foi perdida
		else{
			try {
				if(con.isClosed())
					openConnection();
			} catch (SQLException e) {
				e.printStackTrace();
				openConnection();
			}
		}
		return con;
	}

	/**
	 * Desconecta conex�o com banco de dados
	 */
	public static void closeConnection(){
		try {
			con.close();
			con.clearWarnings();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	/**
	 *  Verifica se existe conex�o estabelecida
	 */
	public static boolean isConnected(){
		if(con==null)
			return false;
		try {
			return !con.isClosed();
		} catch (SQLException e) {
			return false;
		}
	}

	/**
	 *  Executa comando SQL e devolve retorno ResultSet (consulta)
	 */
	public static ResultSet executeQuery(String sqlStatement) throws SQLException{
		System.err.println("sqlStatement: "+sqlStatement);
		ResultSet rs = getConnection().createStatement().executeQuery(sqlStatement);
		return rs;
	}

	/**
	 *  Executa comando SQL (edi��o)
	 */
	public static void executeUpdate(String sqlStatement) throws SQLException{
		System.err.println("sqlStatement: "+sqlStatement);
		getConnection().createStatement().executeUpdate(sqlStatement);
	}
}