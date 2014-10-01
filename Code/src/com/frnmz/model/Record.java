package com.frnmz.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="record")
public class Record implements Comparable<Record>{
	private Date transactionDate;
	private String memberEmail;
	private String memberName;
	private int transactionAmount;
	private int balance;
	private String adminEmail;
	private String creditDebit;
	
	public Date getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	public String getMemberEmail() {
		return memberEmail;
	}
	public void setMemberEmail(String memberEmail) {
		this.memberEmail = memberEmail;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public int getTransactionAmount() {
		return transactionAmount;
	}
	public void setTransactionAmount(int transactionAmount) {
		this.transactionAmount = transactionAmount;
	}
	public int getBalance() {
		return balance;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}
	public String getAdminEmail() {
		return adminEmail;
	}
	public void setAdminEmail(String adminEmail) {
		this.adminEmail = adminEmail;
	}
	public String getCreditDebit() {
		return creditDebit;
	}
	public void setCreditDebit(String creditDebit) {
		this.creditDebit = creditDebit;
	}
	public String getFromTo() {
		String fromTo = memberName;
		
		if(null != memberEmail){
			fromTo = fromTo.concat(" (").concat(memberEmail).concat(")");
		}
		
		return fromTo;
	}
	
	public String getDateString() {
		return new SimpleDateFormat("dd MMM yyyy").format(transactionDate);
	}
	
	@Override
	public int compareTo(Record o) {
		return o.getTransactionDate().compareTo(getTransactionDate());
	}
}