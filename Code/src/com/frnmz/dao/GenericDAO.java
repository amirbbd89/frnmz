package com.frnmz.dao;


public interface GenericDAO<E> {
	boolean insertObject(E object);
	boolean updateObject(E object);
	boolean deleteObject(E object);
}