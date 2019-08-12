package com.palaciossystems;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;

import com.palaciossystems.dao.AdminDao;
import com.palaciossystems.pojo.Admin;

public class MainApp {

	public static void main(String[] args) {

		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring_config.xml");

		AdminDao adminDao = (AdminDao) applicationContext.getBean("adminDao");
		Timestamp ts = new Timestamp(new Date().getTime());

		try{
					
			List<Admin> listAdmins = new ArrayList<Admin>();
			
			listAdmins.add(new Admin(29,"Alfonso Duran","Base de Datos",ts));
			listAdmins.add(new Admin(31,"Evaristo Pino","Programador Junior",ts));
			listAdmins.add(new Admin(32,"Ceferino Vals","Scrum Master",ts));
			listAdmins.add(new Admin(33,"Macola Navarro","Mantenimiento de Software",ts));
			int[] res = adminDao.saveAll(listAdmins);
			
			for ( int i : res) {
				System.out.println("Procesados: "+i);
			}
			
			
		}catch(CannotGetJdbcConnectionException ex){
			System.out.println("Error de Conexión a la base de datos TIPO 1");
			ex.printStackTrace();
		}catch(DataAccessException ex){
			System.out.println("Error de Conexión a la base de datos TIPO 2");
			ex.printStackTrace();
		}catch(Exception ex){
			System.out.println("Error de Conexión a la base de datos TIPO:"+ex.getCause().toString());
			ex.printStackTrace();
		}
		
		((ClassPathXmlApplicationContext)applicationContext).close();
	}

}
