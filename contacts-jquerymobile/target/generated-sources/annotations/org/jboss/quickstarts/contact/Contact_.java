package org.jboss.quickstarts.contact;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Contact.class)
public abstract class Contact_ {

	public static volatile SingularAttribute<Contact, String> firstName;
	public static volatile SingularAttribute<Contact, String> lastName;
	public static volatile SingularAttribute<Contact, String> phoneNumber;
	public static volatile SingularAttribute<Contact, Long> id;
	public static volatile SingularAttribute<Contact, LocalDate> birthDate;
	public static volatile SingularAttribute<Contact, String> email;

	public static final String FIRST_NAME = "firstName";
	public static final String LAST_NAME = "lastName";
	public static final String PHONE_NUMBER = "phoneNumber";
	public static final String ID = "id";
	public static final String BIRTH_DATE = "birthDate";
	public static final String EMAIL = "email";

}

