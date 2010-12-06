package cl.votainteligente.accesointeligente.model;

import cl.votainteligente.accesointeligente.shared.Country;
import cl.votainteligente.accesointeligente.shared.Gender;

import net.sf.gilead.pojo.gwt.LightEntity;

import java.util.Set;

public class User extends LightEntity {
	private static final long serialVersionUID = 6475258234495271306L;

	private Long id;
	private String email;
	private String password;
	private String firstName;
	private String lastName;
	private Age age;
	private Gender gender;
	private Set<Activity> activities;
	private Country country;
	private Region region;
	private Boolean naturalPerson;
	private InstitutionType institutionType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setAge(Age age) {
		this.age = age;
	}

	public Age getAge() {
		return age;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender sex) {
		this.gender = sex;
	}

	public void setActivities(Set<Activity> activities) {
		this.activities = activities;
	}

	public Set<Activity> getActivities() {
		return activities;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public Region getRegion() {
		return region;
	}

	public Boolean getNaturalPerson() {
		return naturalPerson;
	}

	public void setNaturalPerson(Boolean naturalPerson) {
		this.naturalPerson = naturalPerson;
	}

	public void setInstitutionType(InstitutionType institutionType) {
		this.institutionType = institutionType;
	}

	public InstitutionType getInstitutionType() {
		return institutionType;
	}
}
