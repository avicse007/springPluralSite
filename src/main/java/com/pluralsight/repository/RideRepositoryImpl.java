package com.pluralsight.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.pluralsight.model.Ride;
import com.pluralsight.repository.util.RideRowMapper;

@Repository("rideRepository")
public class RideRepositoryImpl implements RideRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<Ride> getRides() {
		List<Ride> rides = jdbcTemplate.query("select * from ride",new RideRowMapper());
		return rides;
	}
	
	@Override
	public Ride getRide(Integer id) {
		Ride ride =   jdbcTemplate.queryForObject("select * from ride where id=?",new RideRowMapper(),id);
		return ride;
	}

	@Override
	public Ride createRide(Ride ride) {
		//Jdbc Template Insert
		jdbcTemplate.update("insert into ride(name,duration) values(?,?)",ride.getName(),ride.getDuration());
		
		
		/*
		//SimpleJdbc Insert Demo
		SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate);
		
		List<String> columns = new ArrayList<>();
		columns.add("name");
		columns.add("duration");
		insert.setTableName("ride");
		insert.setColumnNames(columns);
		Map<String,Object> data = new HashMap<>();
		data.put("name",ride.getName());
		data.put("duration", ride.getDuration());
		insert.setGeneratedKeyName("id");
		Number key = insert.executeAndReturnKey(data);
		System.out.println("Keys===============================>"+key);
		*/
		
		return null;
	}

	@Override
	public Ride updateRide(Ride ride) {
		jdbcTemplate.update("update ride set name=? ,duration=? where id=?",ride.getName(),ride.getDuration(),ride.getId());
		return ride;
	}
	
	@Override
	public void updateRides(List<Object[]> pairs) {
		jdbcTemplate.batchUpdate("update ride set ride_date=? where id=?",pairs);
	}
	
	@Override
	public void deleteRide(Integer id) {
		//jdbcTemplate.update("delete from ride where id=?",id);
		
		//using namedParameterJdbcTemplate
		NamedParameterJdbcTemplate namedTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("id", id);
		namedTemplate.update("delete from ride where id=:id",paramMap);
		
	}
	
}