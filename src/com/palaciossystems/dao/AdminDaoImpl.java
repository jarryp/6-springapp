package com.palaciossystems.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.palaciossystems.pojo.Admin;
import com.palaciossystems.pojo.AdminRowMapper;

@Component("adminDao")
public class AdminDaoImpl implements AdminDao{
	
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Autowired
	private void setDataSource(DataSource dataSource){
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public boolean save(Admin admin) {
		
		/*MapSqlParameterSource paramMap = new MapSqlParameterSource();		
		paramMap.addValue("nombre", admin.getNombre());
		paramMap.addValue("cargo", admin.getCargo());
		paramMap.addValue("fechaCreacion", admin.getFechaCreacion());*/
		
		BeanPropertySqlParameterSource paramMap = new BeanPropertySqlParameterSource(admin);
		
		String xsql = "insert into Admin(nombre,cargo,fechaCreacion) "
				+ "values (:nombre,:cargo,:fechaCreacion)";
		
		return jdbcTemplate.update(xsql, paramMap)==1;
	}
	@Override
	public List<Admin> findAll() {
		return jdbcTemplate.query("select * from Admin", new RowMapper<Admin>(){
			@Override
			public Admin mapRow(ResultSet rs, int numRow) throws SQLException {
				Admin admin = new Admin();
				admin.setIdAd(rs.getInt("idAd"));
				admin.setCargo(rs.getString("cargo"));
				admin.setNombre(rs.getString("nombre"));
				admin.setFechaCreacion(rs.getTimestamp("fechaCreacion"));
				return admin;
			}		
		});
	}
	@Override
	public Admin findById(int id) {
		/*return (Admin) jdbcTemplate.query("select * from Admin where idAd=:idAd",
				new MapSqlParameterSource("idAd", id) ,new AdminRowMapper());*/	
		return jdbcTemplate.queryForObject("select * from Admin where idAd=:idAd", 
				new MapSqlParameterSource("idAd", id), new AdminRowMapper());
	}
	@Override
	public List<Admin> findByCargo(String cargo) {
		return jdbcTemplate.query("select * from Admin where cargo like :cargo", 
				new MapSqlParameterSource("cargo","%" + cargo+ "%" ),
				new AdminRowMapper());
	}
	@Override
	public boolean update(Admin admin) {
		String xsql="update Admin set nombre=:nombre, cargo=:cargo, fechaCreacion=:fechaCreacion where idAd=:idAd ";
		return jdbcTemplate.update(xsql, new BeanPropertySqlParameterSource(admin))==1;
	}
	@Override
	public boolean delete(int idAd) {
		String xsql="delete from Admin where idAd=:idAd";
		return jdbcTemplate.update(xsql, new MapSqlParameterSource("idAd",idAd))==1;
	}

	@Transactional
	@Override
	public int[] saveAll(List<Admin> admins) {
		String xsql = "insert into Admin(idAd,nombre,cargo,fechaCreacion) "
				+ "values (:idAd,:nombre,:cargo,:fechaCreacion)";
		SqlParameterSource[] batchArgs = SqlParameterSourceUtils.createBatch(admins.toArray());
		return jdbcTemplate.batchUpdate(xsql, batchArgs);
		
	}

}
