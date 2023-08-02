package com.lab.task.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Runner.class)
public abstract class Runner_ {

	public static volatile SingularAttribute<Runner, String> password;
	public static volatile SingularAttribute<Runner, Float> delivery_fees;
	public static volatile SingularAttribute<Runner, String> name;
	public static volatile SetAttribute<Runner, Orders> orders;
	public static volatile SingularAttribute<Runner, Long> id;
	public static volatile SingularAttribute<Runner, String> email;
	public static volatile SingularAttribute<Runner, Boolean> status;

}

