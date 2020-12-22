package br.com.tripsapi.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "trip")
public class Trip {

	@DynamoDBHashKey(attributeName = "country")
	private String country;

	@DynamoDBRangeKey(attributeName = "dateTimeCreation")
	private String dateTimeCreation;

	@DynamoDBIndexRangeKey(attributeName = "city", localSecondaryIndexName = "cityIndex")
	private String city;

	@DynamoDBIndexRangeKey(attributeName = "reason", localSecondaryIndexName = "reasonIndex")
	private String reason;

	public Trip(String country, String dateTimeCreation, String city, String reason) {
		super();
		this.country = country;
		this.dateTimeCreation = dateTimeCreation;
		this.city = city;
		this.reason = reason;
	}

	public Trip() {
		super();
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getDateTimeCreation() {
		return dateTimeCreation;
	}

	public void setDateTimeCreation(String dateTimeCreation) {
		this.dateTimeCreation = dateTimeCreation;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}
