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
		Admin admin = new Admin();		
		admin.setCargo("Analista Base de Datos");
		admin.setNombre("Luis Perez");
		admin.setFechaCreacion(ts);
		
		/*
		if(adminDao.save(admin)){
			System.out.println("Registro guardado satisfactoriamente");
		}else{
			System.out.println("Registro no almacenado en la base de datos");
		}*/
		
		try{
			//adminDao.save(admin);
			//System.out.println("Registro Agregado");
			
			
			List<Admin> admins = adminDao.findAll();
			for(Admin adm : admins){
				System.out.println(adm);
			}
			
			System.out.println("");
			System.out.println("MOSTRANDO SEGÙN CODIGO");
			System.out.println(adminDao.findById(2));
			System.out.println(adminDao.findById(4));
			System.out.println("");
			System.out.println("MOSTRANDO SEGUN CARGOS");
			System.out.println(adminDao.findByCargo("Programador").toString());
			
			
			Admin adminC = adminDao.findById(2);
			adminC.setCargo("Jefe de Todos");
			if(adminDao.update(adminC)){
				System.out.println("Registro Actualizado: "+adminC);
			}
			
			if(adminDao.delete(adminC.getIdAd())){
				System.out.println("Registro Eliminado: "+adminC.getNombre());
			}
			
			
			
			List<Admin> listAdmins = new ArrayList<Admin>();
			
			listAdmins.add(new Admin("Jarry Palacios","Base de Datos",ts));
			listAdmins.add(new Admin("Jarold Palacios","Programador Junior",ts));
			listAdmins.add(new Admin("Kairy Palacios","Scrum Master",ts));
			listAdmins.add(new Admin("Javier Palacios","Mantenimiento de Software",ts));
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
