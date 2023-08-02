package com.lab.task.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Orders.class)
public abstract class Orders_ {

	public static volatile SingularAttribute<Orders, String> order_status;
	public static volatile SingularAttribute<Orders, Resturant> resturantData;
	public static volatile SingularAttribute<Orders, Float> total_price;
	public static volatile SingularAttribute<Orders, Runner> RunnerData;
	public static volatile SingularAttribute<Orders, Long> id;
	public static volatile SetAttribute<Orders, Meal> items;
	public static volatile SingularAttribute<Orders, String> Date;

}

